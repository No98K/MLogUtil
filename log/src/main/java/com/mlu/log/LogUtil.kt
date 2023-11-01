package com.mlu.log

import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object LogUtil {
    private val isShow = true
    private val TAG = "mwb"

    private val start =
        " ╔══════════════════════════════════════════════════════════════════════════════════════════════════"

    private val line =
        "╟──────────────────────────────────────────────────────────────────────────────────────────────────"
    private val content = "║ "
    private val end =
        " ╚══════════════════════════════════════════════════════════════════════════════════════════════════"
    private val kong = "\u0020"

    private val DEFAULT_SAVE_PATH =
        Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_DOCUMENTS + "/HJ"

    /**
     * 打印所有的日志
     */
    fun all_i(msg: String) {
        var msg = msg
        if (isShow) {
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            val max_str_length: Int = 2001 - TAG.length
            //大于4000时
            while (msg.length > max_str_length) {
                Log.i(TAG, msg.substring(0, max_str_length))
                msg = msg.substring(max_str_length)
            }
            Log.i(TAG, msg)
        }
    }

    /**
     * 打印所有的日志
     */
    fun all_d(msg: String) {
        var msg = msg
        if (isShow) {
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            val max_str_length: Int = 2001 - TAG.length
            //大于4000时
            while (msg.length > max_str_length) {
                Log.d(TAG, msg.substring(0, max_str_length))
                msg = msg.substring(max_str_length)
            }
            Log.d(TAG, msg)
        }
    }

    /**
     * 打印所有的日志
     */
    fun all_e(msg: String) {
        var msg = msg
        if (isShow) {
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            val max_str_length: Int = 2001 - TAG.length
            //大于4000时
            while (msg.length > max_str_length) {
                Log.e(TAG, msg.substring(0, max_str_length))
                msg = msg.substring(max_str_length)
            }
            Log.e(TAG, msg)
        }
    }

    /**
     * 打印所有的日志
     */
    fun all_w(msg: String) {
        var msg = msg
        if (isShow) {
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            val max_str_length: Int = 2001 - TAG.length
            //大于4000时
            while (msg.length > max_str_length) {
                Log.w(TAG, msg.substring(0, max_str_length))
                msg = msg.substring(max_str_length)
            }
            Log.w(TAG, msg)
        }
    }

    fun i(msg: String, isSave: Boolean = false) {
        if (!isShow) {
            return
        }

        val stackTrace = Thread.currentThread().stackTrace
        val caller = stackTrace[3]

        Log.i(
            TAG, start + "\n"
                    + kong + content + "Thread: ${Thread.currentThread().name}" + "\n"
                    + kong + line + "\n"
                    + kong + content + "$caller" + "\n"
                    + kong + line + "\n"
                    + kong + content + msg + "\n"
                    + end
        )

        // 是否存储日志
        if (isSave) {
            val saveMsg = "[i]" + "Thread: ${Thread.currentThread().name}" + ",$caller," + msg
            saveMessage(saveMsg, DEFAULT_SAVE_PATH)
        }
    }

    fun w(msg: String, isSave: Boolean = false) {
        if (!isShow) {
            return
        }

        val stackTrace = Thread.currentThread().stackTrace
        val caller = stackTrace[3]

        Log.w(
            TAG, start + "\n"
                    + kong + content + "Thread: ${Thread.currentThread().name}" + "\n"
                    + kong + line + "\n"
                    + kong + content + "$caller" + "\n"
                    + kong + line + "\n"
                    + kong + content + msg + "\n"
                    + end
        )

        // 是否存储日志
        if (isSave) {
            val saveMsg = "[w]" + "Thread: ${Thread.currentThread().name}" + ",$caller," + msg
            saveMessage(saveMsg, DEFAULT_SAVE_PATH)
        }
    }

    fun e(msg: String, isSave: Boolean = false) {
        if (!isShow) {
            return
        }

        val stackTrace = Thread.currentThread().stackTrace
        val caller = stackTrace[3]

        Log.e(
            TAG, start + "\n"
                    + kong + content + "Thread: ${Thread.currentThread().name}" + "\n"
                    + kong + line + "\n"
                    + kong + content + "$caller" + "\n"
                    + kong + line + "\n"
                    + kong + content + msg + "\n"
                    + end
        )

        // 是否存储日志
        if (isSave) {
            val saveMsg = "[e]" + "Thread: ${Thread.currentThread().name}" + ",$caller," + msg
            saveMessage(saveMsg, DEFAULT_SAVE_PATH)
        }
    }

    fun d(msg: String, isSave: Boolean = false) {
        if (!isShow) {
            return
        }

        val stackTrace = Thread.currentThread().stackTrace
        val caller = stackTrace[3]

        Log.d(
            TAG, start + "\n"
                    + kong + content + "Thread: ${Thread.currentThread().name}" + "\n"
                    + kong + line + "\n"
                    + kong + content + "$caller" + "\n"
                    + kong + line + "\n"
                    + kong + content + msg + "\n"
                    + end
        )

        // 是否存储日志
        if (isSave) {
            val saveMsg = "[d]" + "Thread: ${Thread.currentThread().name}" + ",$caller," + msg
            saveMessage(saveMsg, DEFAULT_SAVE_PATH)
        }
    }

    fun saveMessage(msg: String, path: String) {
        if (msg == null || msg.isEmpty()) {
            return
        }

        if (path.isNullOrEmpty()) {
            return
        }


        try {
            // 获取当前日期
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            val todayDate = dateFormat.format(currentDate)

            // 构建文件路径
            val filePath = "$path/$todayDate.txt"

            // 确保目录存在，如果不存在则创建
            val directory = File(path)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            // 将消息写入文件
            val writer = BufferedWriter(FileWriter(filePath, true))

            val saveDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            val saveDate = saveDateFormat.format(currentDate)
            val writeContent = "【$saveDate】" + msg

            writer.write(writeContent)
            writer.newLine() // 换行
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}