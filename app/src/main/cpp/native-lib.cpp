#include <jni.h>
#include <string>
#include <fstream>
#include <sstream>
#include <sys/utsname.h>

#define PNCODE_SYSNAME 0
#define PNCODE_NODE_NAME 1
#define PNCODE_RELEASE 2
#define PNCODE_VERSION 3
#define PNCODE_MACHINE 4
#define PNCODE_UNKNOWN "UKNOWN CODE"



std::string stdStringFromJString(JNIEnv *env, jstring inputJString){
    std::string retValueStr{};

    jboolean isCopy;
    const char *convertedValue = (env)->GetStringUTFChars(inputJString, &isCopy);
    if(convertedValue)
    {
        retValueStr = std::string(convertedValue);
        env->ReleaseStringUTFChars(inputJString, convertedValue);
    }

    return retValueStr;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_pkgsinfo_MainActivity_getFileTextBuffer(
        JNIEnv* env,
        jobject /* this */, jstring fileNameJ) {


    std::string ret_val_str;

    std::string fileName = stdStringFromJString(env, fileNameJ);
    std::ifstream file;
    file.open(fileName);
    if(file.is_open())
    {
        std::ostringstream ss;
        ss << file.rdbuf();
        ret_val_str = ss.str();
    }
    else
        ret_val_str = "Cannot open file " + fileName;

    return env->NewStringUTF(ret_val_str.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_pkgsinfo_MainActivity_nativeUname(
        JNIEnv* env,
    jobject /* this */, jint queryCode) {

    std::string retStrValue{};

    struct utsname buffer;
    if (uname(&buffer) != 0)
        retStrValue = "uname error\n";
    else
        switch((int)queryCode) {
        case PNCODE_SYSNAME:
            retStrValue = buffer.sysname;
            break;
        case PNCODE_NODE_NAME:
            retStrValue = buffer.nodename;
            break;
        case PNCODE_RELEASE:
            retStrValue = buffer.release;
            break;
        case PNCODE_VERSION:
            retStrValue = buffer.version;
            break;
        case PNCODE_MACHINE:
            retStrValue = buffer.machine;
            break;
        default:
            retStrValue = PNCODE_UNKNOWN;
            break;
        }

    return env->NewStringUTF(retStrValue.c_str());
}