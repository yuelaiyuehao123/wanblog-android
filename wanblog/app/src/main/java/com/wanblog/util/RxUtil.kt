package com.wanblog.util

import android.widget.Toast
import com.wanblog.base.App
import com.wanblog.log.LogUtil
import com.wanblog.model.bean.MyHttpResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.anko.getStackTraceString

object RxUtil {

    private val ERROR_LOG_TAG: String = "http"

    /**
     * 统一线程处理
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): FlowableTransformer<T, T> {    //compose简化线程
        return FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 处理返回结果
     */
    fun <T> handleResult(): FlowableTransformer<MyHttpResponse<T>, T> {
        return FlowableTransformer<MyHttpResponse<T>, T> {
            it.flatMap {
                createData(it.data)
            }
        }
    }

    /**
     * 生成Flowable
     * @param <T>
     * @return
    </T> */
    fun <T> createData(t: T): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                if (t != null) {
                    emitter.onNext(t)
                } else {
                    Toast.makeText(App.instance, "服务器数据错误", Toast.LENGTH_SHORT).show()
                }
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
                Toast.makeText(App.instance, "服务器数据异常", Toast.LENGTH_SHORT).show()
                LogUtil.d(ERROR_LOG_TAG, e.getStackTraceString())
            }
        }, BackpressureStrategy.BUFFER)
    }

}