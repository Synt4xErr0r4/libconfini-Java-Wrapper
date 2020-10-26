#include "confini.h"
#include "at_syntaxerror_ini_IniUtil.h"

#include <string>
#include <iostream>

using namespace std;

bool DEBUG=false;

#define log(str) if(DEBUG) {cout<<"[NATIVE] "<<str<<endl;}

JNIEXPORT void JNICALL Java_at_syntaxerror_ini_IniUtil_set_1implicit_1value0(JNIEnv*env,jclass,jstring value,jint len) {
    const char*chars=env->GetStringUTFChars(value,JNI_FALSE);
    ini_global_set_implicit_value(const_cast<char*>(chars),len);

    log("set_implicit_value: "<<string(chars));

    env->ReleaseStringUTFChars(value,chars);
}

JNIEXPORT void JNICALL Java_at_syntaxerror_ini_IniUtil_enable_1debug (JNIEnv *, jclass) {
    DEBUG=true;
}

jint get_enum_int(JNIEnv*env,jobject constant,string clazz) {
    log("get_enum_int: "<<clazz);

    jclass at_syntaxerror_ini_class=env->FindClass(("at/syntaxerror/ini/"+clazz).c_str());

    log("get_enum_int: "<<clazz<<" found");

    jmethodID getValue=env->GetMethodID(at_syntaxerror_ini_class,"getValue","()I");

    log("get_enum_int: "<<clazz<<"#getValue()I found");

    return env->CallIntMethod(constant,getValue);
}
jchar get_enum_char(JNIEnv*env,jobject constant,string clazz) {
    log("get_enum_char: "<<clazz);

    jclass at_syntaxerror_ini_class=env->FindClass(("at/syntaxerror/ini/"+clazz).c_str());

    log("get_enum_char: "<<clazz<<" found");

    jmethodID getValue=env->GetMethodID(at_syntaxerror_ini_class,"getValue","()C");

    log("get_enum_char: "<<clazz<<"#getValue()C found");

    return env->CallCharMethod(constant,getValue);
}

jboolean get_bool(JNIEnv*env,jobject obj,string clazz,string name) {
    log("get_bool: "<<clazz<<"#"<<name);

    jclass at_syntaxerror_ini_class=env->FindClass(("at/syntaxerror/ini/"+clazz).c_str());

    log("get_bool: "<<clazz<<" found");

    jfieldID field=env->GetFieldID(at_syntaxerror_ini_class,name.c_str(),"Z");

    log("get_bool: "<<clazz<<"#"<<name<<"found");

    return env->GetBooleanField(obj,field);
}
jobject get_obj(JNIEnv*env,jobject obj,string clazz,string name,string type) {
    log("get_object: "<<clazz<<"#"<<name);

    jclass at_syntaxerror_ini_class=env->FindClass(("at/syntaxerror/ini/"+clazz).c_str());

    log("get_object: "<<clazz<<" found");

    jfieldID field=env->GetFieldID(at_syntaxerror_ini_class,name.c_str(),("Lat/syntaxerror/ini/"+type+";").c_str());

    log("get_object: "<<clazz<<"#"<<name<<"found");

    return env->GetObjectField(obj,field);
}

IniFormat from_object(JNIEnv*env,jobject obj) {
    return ((IniFormat) {
      .delimiter_symbol = (unsigned char)get_enum_char(env,get_obj(env,obj,"IniFormat","delimiter_symbol","IniDelimiters"),"IniDelimiters"),
      .case_sensitive = get_bool(env,obj,"IniFormat","case_sensitive"),
      .semicolon_marker = (unsigned char)get_enum_int(env,get_obj(env,obj,"IniFormat","semicolon_marker","IniCommentMarker"),"IniCommentMarker"),
      .hash_marker = (unsigned char)get_enum_int(env,get_obj(env,obj,"IniFormat","hash_marker","IniCommentMarker"),"IniCommentMarker"),
      .section_paths = (unsigned char)get_enum_int(env,get_obj(env,obj,"IniFormat","section_paths","IniSectionPaths"),"IniSectionPaths"),
      .multiline_nodes = (unsigned char)get_enum_int(env,get_obj(env,obj,"IniFormat","multiline_nodes","IniMultiline"),"IniMultiline"),
      .no_single_quotes = get_bool(env,obj,"IniFormat","no_single_quotes"),
      .no_double_quotes = get_bool(env,obj,"IniFormat","no_double_quotes"),
      .no_spaces_in_names = get_bool(env,obj,"IniFormat","no_spaces_in_names"),
      .implicit_is_not_empty = get_bool(env,obj,"IniFormat","implicit_is_not_empty"),
      .do_not_collapse_values = get_bool(env,obj,"IniFormat","do_not_collapse_values"),
      .preserve_empty_quotes = get_bool(env,obj,"IniFormat","preserve_empty_quotes"),
      .disabled_after_space = get_bool(env,obj,"IniFormat","disabled_after_space"),
      .disabled_can_be_implicit = get_bool(env,obj,"IniFormat","disabled_can_be_implicit")
    });
}

jobject to_enum_from_char(JNIEnv*env,string enumclass,jchar chr) {
    log("to_enum_from_char: "<<enumclass);

    jclass at_syntaxerror_ini_enum=env->FindClass(("at/syntaxerror/ini/"+enumclass).c_str());

    log("to_enum_from_char: at/syntaxerror/ini/"<<enumclass<<" found");


    jmethodID fromValue=env->GetStaticMethodID(at_syntaxerror_ini_enum,"fromValue",("(C)Lat/syntaxerror/ini/"+enumclass+";").c_str());

    if(fromValue==NULL) {
        log("to_enum_from_char: Couldn't find at/syntaxerror/ini/"<<enumclass<<"#fromValue(C)Lat/syntaxerror/ini/"<<enumclass);
        env->ThrowNew(env->FindClass("java/lang/RuntimeException"),"Cannot find fromValue method");
        return NULL;
    }

    log("to_enum_from_char: at/syntaxerror/ini/"<<enumclass<<"#fromValue(C)Lat/syntaxerror/ini/"<<enumclass<<" found");

    return env->CallStaticObjectMethod(at_syntaxerror_ini_enum,fromValue,chr);
}
jobject to_enum_from_int(JNIEnv*env,string enumclass,jint val) {
    log("to_enum_from_int: "<<enumclass);

    jclass at_syntaxerror_ini_enum=env->FindClass(("at/syntaxerror/ini/"+enumclass).c_str());

    log("to_enum_from_int: at/syntaxerror/ini/"<<enumclass<<" found");

    jmethodID fromValue=env->GetStaticMethodID(at_syntaxerror_ini_enum,"fromValue",("(I)Lat/syntaxerror/ini/"+enumclass+";").c_str());

    if(fromValue==NULL) {
        log("to_enum_from_int: Couldn't find at/syntaxerror/ini/"<<enumclass<<"#fromValue(I)Lat/syntaxerror/ini/"<<enumclass);
        env->ThrowNew(env->FindClass("java/lang/RuntimeException"),"Cannot find fromValue method");
        return NULL;
    }

    log("to_enum_from_int: at/syntaxerror/ini/"<<enumclass<<"#fromValue(I)Lat/syntaxerror/ini/"<<enumclass<<" found");

    return env->CallStaticObjectMethod(at_syntaxerror_ini_enum,fromValue,val);
}

jobject to_object_format(JNIEnv*env,IniFormat fmt) {
    log("to_object_format");

    jclass at_syntaxerror_ini_IniFormat=env->FindClass("at/syntaxerror/ini/IniFormat");

    log("to_object_format: at/syntaxerror/ini/IniFormat found");

    jmethodID constructor=env->GetMethodID(at_syntaxerror_ini_IniFormat,"<init>","(Lat/syntaxerror/ini/IniDelimiters;Lat/syntaxerror/ini/IniCommentMarker;Lat/syntaxerror/ini/IniCommentMarker;Lat/syntaxerror/ini/IniMultiline;Lat/syntaxerror/ini/IniSectionPaths;ZZZZZZZZZ)V");

    if(constructor==NULL) {
        log("CONSTRUCTUR NOT FOUND @ FORMAT");
    } else log("CONSTRUCTUR NOT FOUND @ FORMAT");

    log("to_object_format: at/syntaxerror/ini/IniFormat#<init>(...) found");

    return env->NewObject(at_syntaxerror_ini_IniFormat,constructor,
        to_enum_from_char(env,"IniDelimiters",fmt.delimiter_symbol),
        to_enum_from_int(env,"IniCommentMarker",fmt.semicolon_marker),
        to_enum_from_int(env,"IniCommentMarker",fmt.hash_marker),
        to_enum_from_int(env,"IniMultiline",fmt.multiline_nodes),
        to_enum_from_int(env,"IniSectionPaths",fmt.section_paths),
        (jboolean)fmt.case_sensitive,
        (jboolean)fmt.no_single_quotes,
        (jboolean)fmt.no_double_quotes,
        (jboolean)fmt.no_spaces_in_names,
        (jboolean)fmt.implicit_is_not_empty,
        (jboolean)fmt.do_not_collapse_values,
        (jboolean)fmt.preserve_empty_quotes,
        (jboolean)fmt.disabled_after_space,
        (jboolean)fmt.disabled_can_be_implicit
    );
}

jobject to_object_dispatch(JNIEnv*env,IniDispatch disp) {
    log("to_object_dispatch");

    jclass at_syntaxerror_ini_IniDispatch=env->FindClass("at/syntaxerror/ini/IniDispatch");

    log("to_object_dispatch: at/syntaxerror/ini/IniDispatch found");

    jmethodID constructor=env->GetMethodID(at_syntaxerror_ini_IniDispatch,"<init>","(Lat/syntaxerror/ini/IniFormat;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V");

    log("to_object_dispatch: at/syntaxerror/ini/IniDispatch#<init>(...) found");

    if(constructor==NULL) {
        log("CONSTRUCTUR NOT FOUND @ DISPATCH");
    } else log("CONSTRUCTUR FOUND @ DISPATCH");
    
    return env->NewObject(at_syntaxerror_ini_IniDispatch,constructor,
        to_object_format(env,disp.format),
        (jint)disp.type,
        env->NewStringUTF(disp.data),
        env->NewStringUTF(disp.value),
        env->NewStringUTF(disp.append_to),
        (jint)disp.dispatch_id
    );
}

jint function_apply(JNIEnv*env,jobject consumer,IniDispatch value) {
    log("function_apply");

    jclass java_util_function_Consumer=env->FindClass("java/util/function/Function");

    log("function_apply: java/util/function/Function found");

    jmethodID accept=env->GetMethodID(java_util_function_Consumer,"apply","(Ljava/lang/Object;)Ljava/lang/Object;");

    if(accept==NULL) {
        log("Couldn't find java/util/function/Function#apply(Ljava/lang/Object;)Ljava/lang/Object;");
        return 1;
    }
    
    log("function_apply: java/util/function/Function#apply(Ljava/lang/Object;)Ljava/lang/Object; found");

    jobject retval=env->CallObjectMethod(consumer,accept,to_object_dispatch(env,value));

    log("function_apply: got java/lang/Integer");

    if(retval==NULL) {
        log("Function returned null");
        return 1;
    }

    jclass java_lang_Integer=env->FindClass("java/lang/Integer");

    log("function_apply: found java/lang/integer");

    jmethodID intValue=env->GetMethodID(java_lang_Integer,"intValue","()I");

    log("function_apply: found java/lang/integer#intValue()I");

    return env->CallIntMethod(retval,intValue);
}

struct wrapper_data {
    JNIEnv*env;
    jobject consumer;
};


static jint handle(IniDispatch*disp,void*val) {
    log("handle");

    struct wrapper_data* data=((struct wrapper_data*)val);
    return function_apply(data->env,data->consumer,*disp);
}

JNIEXPORT jint JNICALL Java_at_syntaxerror_ini_IniUtil_load_1ini_1path0(JNIEnv*env,jclass,jstring file,jobject format,jobject consumer) {
    IniFormat fmt=INI_DEFAULT_FORMAT;
    const char*chars=env->GetStringUTFChars(file,JNI_FALSE);

    log("Parsing file "<<chars<<"...");

    if(format!=NULL)
        fmt=from_object(env,format);
    
    struct wrapper_data data;
    data.env=env;
    data.consumer=consumer;

    jint i=load_ini_path(chars,fmt,NULL,handle,&data);
    env->ReleaseStringUTFChars(file,chars);

    return i;
}