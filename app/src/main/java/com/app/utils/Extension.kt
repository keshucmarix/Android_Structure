package com.app.utils

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.app.ui.base.BaseFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

fun <T> MutableLiveData<Resource<T>>.collectData(
    response: Response<T>,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch(Dispatchers.Main) {
        try {
            if (response.code() == 200) {
                this@collectData.value = Resource.success(data = response.body())
            } else {
                this@collectData.value =
                    Resource.error(response.errorBody().toString(), data = null)
            }
        } catch (e: Exception) {
            this@collectData.value = Resource.error(e.message ?: "Error Occurred!", data = null)
        }
    }
}

fun <T> MutableLiveData<Resource<T>>.collectDataNew(
    response: Response<T>,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch(Dispatchers.Main) {
        try {
            if (response.code() == 200) {
                this@collectDataNew.value = Resource.success(data = response.body())
                coroutineScope.launch(context = Dispatchers.IO) {
                }
            } else {
                this@collectDataNew.value =
                    Resource.error(response.errorBody().toString(), data = null)
            }
        } catch (e: Exception) {
            this@collectDataNew.value = Resource.error(e.message ?: "Error Occurred!", data = null)
        }
    }
}

fun <T> MutableLiveData<Resource<T>>.observer(
    lifecycleOwner: LifecycleOwner,
    success: (T) -> Unit,
    failure: ((String) -> Unit?)? = null,
) {
    this.observe(lifecycleOwner) {
        when (it.status) {
            Status.SUCCESS -> {
                it.data?.let { it1 -> success(it1) }
            }

            Status.ERROR -> {
                if (failure == null) {
                    it.message?.let { it1 ->
                        (lifecycleOwner as BaseFragment<*>).onException(
                            NoConnectivityException(message = it1)
                        )
                    }
                } else {
                    it.message?.let { it1 -> failure.let { it2 -> it2(it1) } }
                }
            }

            Status.EXEPTION -> {
                it.exception?.let { it1 -> (lifecycleOwner as BaseFragment<*>).onException(it1) }
            }
        }
    }
}

fun <T> MutableLiveData<Resource<T>>.collectData(
    response: Resource<T>? = null,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch(Dispatchers.Main) {

        try {
            if (response?.data != null) {

                this@collectData.value = Resource.success(data = response.data)


            } else {
                this@collectData.value =
                    Resource.error(response?.message.toString(), data = null)
            }
        } catch (e: NoConnectivityException) {
            this@collectData.value = Resource.error(e.message ?: "Error Occurred!", data = null)
        } catch (e: Exception) {
            this@collectData.value = Resource.error(e.message ?: "Error Occurred!", data = null)
        }
    }
}

fun <T> MutableLiveData<Resource<T>>.Exception(
    e: Exception
) {
    if (e is HttpException) {
        this@Exception.value = Resource.exception(e)

    } else {
        this@Exception.value = Resource.error(e.message ?: "Error Occurred!", data = null)
    }
}


fun CoroutineScope.launchWithExceptionHandling(
    block: suspend (CoroutineScope) -> Unit,
    error: suspend (Exception) -> Unit,
) {
    this.apply {
        launch(Dispatchers.IO) {
            try {
                block(this)

            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    error(e)
                }
            }
        }
    }
}

inline fun <reified T> Any.convertAnyToModel(): T {
    return Gson().fromJson(Gson().toJson(this), T::class.java) as T
}

fun AppCompatTextView.getTextValue(): String {
    return this.text.toString()
}

fun AppCompatEditText.getTextValue(): String {
    return this.text.toString()
}