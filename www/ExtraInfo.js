/**
 * Created by ryp on 1/22/16.
 */
var exec = require('cordova/exec');
var OffLineStorage={
    //data and params must be array
    saveData:function(data,success, error) {
        exec(success, error,"ExtraInfo","saveData", data);
    },
    getData:function(params,success, error) {
        exec(success, error,"ExtraInfo","getData", params);
    }
}
exports.OffLineStorage = OffLineStorage;


