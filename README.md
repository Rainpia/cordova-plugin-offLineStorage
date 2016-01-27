This is a cordova plugin about android off line storage data,mainly save request and response of api.

1,use it need change some space,

update cordova-plugin-offLineStorage/src/android/DBUtil.java  line 15

private static final String   DATABASE_POSITION    = "/data/data/io.cordova.hellocordova/";

need change "io.cordova.hellocordova" to your android activity package path

2,
    "saveData" method need params is an json array that have two json,first is request,second is response.
    "getData" method need params is an json array that have one json,it is request.

3,because cordova update,its plugin added  need console line.so this plugin use it need :
    first,upload the package,copy it to your cordova project plugins path.
    second, change android.json and fetch.json,add this plugin some json message.

4,example:

            var ExtraInfo = cordova.require('cordova-plugin-offLineStorage.extrainfo');

            var data1=[{url:'api/login',data:{}},{res:{},code:200,message:'have data'}];
            var data2=[{url:'api/getData',data:{}},{res:{},code:200,message:'have data'}];
            var data3=[{url:'api/validation',data:{}},{res:{},code:200,message:'validation success'}];

            ExtraInfo.OffLineStorage.saveData(data1,function(res) {

                document.getElementById("data").innerHTML=res.message;

            }, function(error) {

                document.getElementById("data").innerHTML=error.message;

            });

            ExtraInfo.OffLineStorage.getData([data1[0]],function(res) {

                    document.getElementById("data").innerHTML=res.data;

            }, function(error) {

                    document.getElementById("data").innerHTML=error.message;

            });