package com.example.careold.common;

/**
 * 功能：
 *
 */
public class DefineData {
    private DefineData(){}

    /**
     * 平台协议用
     * 消息前缀
     */
    public static final String PREFIX = "com.sitech.APP";
    /**
     * 消息中间件请求地址
     */
    public static final String AM_URL = "http://172.21.10.92:8080/mqtt/request";
    //public static final String AM_URL = "http://172.21.221.227:8080/mqtt/request";

    /**
     * 返回值 消息ID
     */
    public static final String ERROR_CODE = "errorCode";
    /**
     * 消息体
     */
    public static final String ERROR_MESSAGE = "errorMessage";
    /**
     * 处理状态 成功
     */
    public static final String STATUS_SUCCESS = "0";
    /**
     * 处理中
     */
    public static final String STATUS_PROCESSING = "1";
    /**
     * 删除
     */
    public static final String STATUS_DELETE = "2";
    /**
     * 失败
     */
    public static final String STATUS_FAILED = "9";
    /**
     * 整个事务请求成功 事务请求成功
     */
    public static final String TRANSACTION_SUCCESS_CODE = "0000";
    /**
     * 事务请求失败
     */
    public static final String TRANSACTION_FAILED_CODE = "9999";

    /**
     * DB操作
     * DB查询失败
     */
    public static final String DB_SELECT_FAILED = "2000";
    /**
     * DB更新失败
     */
    public static final String DB_UPDATE_FAILED = "2001";
    /**
     * DB删除失败
     */
    public static final String DB_DELETE_FAILED = "2002";
    /**
     * DB插入失败
     */
    public static final String DB_INSERT_FAILED = "2003";
    /**
     * 图片操作 图片保存失败
     */
    public static final String PHOTO_SAVE_FAILED = "4511";
    /**
     * 图片保存成功
     */
    public static final String PHOTO_SAVE_SUCCESS = "4510";
    /**
     * HTTP请求出错
     */
    public static final String REQUEST_HTTP_FAILED = "1111";
    /**
     * MQTT请求出错
     */
    public static final String REQUEST_MQTT_FAILED = "2222";

    /**
     * 开发环境用
     */


    /**
     * 测试环境用
     */

    /**
     * 人员管理接口响应码 注册人员服务编号
     */
    public static final String ADD_PERSON_CODE = "4000";
    /**
     * 删除人员服务编号
     */
    public static final String DELETES_PERSON_CODE = "4001";
    /**
     * 清空人员服务编号
     */
    public static final String CLEAR_PERSON_CODE = "4002";
    /**
     * 查询所有人员服务编号
     */
    public static final String GETS_PERSON_CODE = "4003";
    /**
     * 查询人员图像服务编号
     */
    public static final String GETS_FACE_PERSON_CODE = "4004";

    /**
     * 查询通行记录
     */
    public static final String GET_RECONGNITION_CODE = "4005";
    /**
     * 查询通行记录总数
     */
    public static final String GET_RECONGNITION_NUM_CODE = "4006";

    /**
     * 权限管理接口响应码 添加用户权限
     */
    public static final String ADD_PERMISSION_CODE = "4007";
    /**
     * 删除用户权限
     */
    public static final String DELETE_PERMISSION_CODE = "4008";
    /**
     * 查询用户权限
     */
    public static final String QUERY_PERMISSION_CODE = "4009";
    /**
     * 设备管理响应码 设备参数查询
     */
    public static final String GETS_DEVICES_CODE = "4010";
    /**
     * 设备参数设置
     */
    public static final String SETS_DEVICE_CODE = "4011";



}
