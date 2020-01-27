
 <h1 class="curproject-name"> minoss-admin </h1> 
 


# 用户模块

## 用户登录接口
<a id=用户登录接口> </a>
### 基本信息

**Path：** /api/admin/user/login

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/x-www-form-urlencoded | 是  |   |   |
**Body**

| 参数名称  | 参数类型  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| loginName | text  |  是 |  pencilso  |  账号 |
| loginPassword | text  |  是 |  1237654321  |  密码 |



### 返回数据

```javascript
{
   "code": 0,
   "data": {
      "accesstoken": "eyJhbGciOiJIUzUxMiJ9.eyJ1X2lkIjo2NzAyNDE0MTI0OTQ3OTA2NTYsInVfc2FsdCI6Ijg5NGQ5ZTQ1OTMzZTQ1Y2I4MWFlN2NhZDZjOTUwOWQ1IiwibF90aW1lIjoxNTc5OTY1MjQ2NzEwfQ.n78YOp-AyQBQt_qVl9IhshJPNVRxMGsA-I7b9D7NH_pENLK_3mHzoBEcF_bPf4Yg0_HSAGw6waQt16othdTTzQ"
   },
   "message": "操作成功"
}
```
# 存储空间

## 新增-存储空间
<a id=新增-存储空间> </a>
### 基本信息

**Path：** /api/admin/bucket/insertbucket

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Body**

```javascript
{
    //bucket 名称
	"bucketName":"这是一个测试bocket",
	//bucket 本地存储路径
	"bucketStorePath":"store/bucket",
	//bucket 权限  1私有  2公共读
	"bucketRight":1
}
```
### 返回数据

```javascript
{
   "code": 0,
   "data": null,
   "message": "操作成功"
}
```
## 更新-存储空间
<a id=更新-存储空间> </a>
### 基本信息

**Path：** /api/admin/bucket/updatebucket

**Method：** POST

**接口描述：**


### 请求参数
**Headers**

| 参数名称  | 参数值  |  是否必须 | 示例  | 备注  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| Content-Type  |  application/json | 是  |   |   |
**Body**

```javascript
{
    //存储空间业务ID
	"mid":"1221732542747549697",
	//名称
	"bucketName":"这是一个测试bocket-1",
	//本地存储路径
	"bucketStorePath":"store/bucket",
	//权限
	"bucketRight":1
}
```
### 返回数据

```javascript
{
   "code": 0,
   "data": null,
   "message": "操作成功"
}
```
## 查询-存储空间列表
<a id=查询-存储空间列表> </a>
### 基本信息

**Path：** /api/admin/bucket/querybucketlist

**Method：** GET

**接口描述：**


### 请求参数

### 返回数据

```javascript
{
    "code": 0,
    "data": [
        {
            //存储空间名
            "bucketName": "bucket-1",
            //bucket 存储到本地的路径
            "bucketStorePath": "",
            //权限 1私有 2公共读
            "bucketRight": 1,
            //存储空间ID
            "bucketMid": "570241412494790656",
            //已使用大小 （字节）
            "storeUsedSize": 1024,
            //已存储文件数量
            "storeFileSize": 11,
            //创建时间
            "createDate": "2020-01-25 14:19:10",
            //更新时间
            "updateDate": "2020-01-25 15:56:02"
        }
    ],
    "message": "操作成功"
}
```