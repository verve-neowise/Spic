package com.neowise.spic.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RunIO<T> {

    private var _onSuccess: ( (value: T) -> Unit )? = null
    private var _onFailure: ( (e: Exception) -> Unit )? = null
    private var _operation: ( suspend () -> T )? = null
    private var _finally: ( () -> Unit )? = null

    fun  run(_operation: suspend () -> T): RunIO<T> {
        this._operation = _operation
        return this
    }

    fun onSuccess(_onSuccess: (value: T) -> Unit): RunIO<T> {
        this._onSuccess = _onSuccess
        return this
    }

    fun onFailure(_onFailure: (e: Exception) -> Unit): RunIO<T> {
        this._onFailure = _onFailure
        return this
    }

    fun finally(finally: () -> Unit): RunIO<T> {
        this._finally = finally
        return this
    }

    fun execute() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                _operation?.let {
                    val value = it.invoke()

                    launch(Dispatchers.Main) {
                        _onSuccess?.invoke(value)
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace(System.err)
                launch(Dispatchers.Main) {
                    _onFailure?.invoke(e)
                }
            }

            launch(Dispatchers.Main) {
                _finally?.invoke()
            }
        }
    }
}