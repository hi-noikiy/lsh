/**
 * Created by Administrator on 14-10-27.
 */
//涨跌点数计算
function difference(closeprice,pRec,decimal )
{
   var result= parseFloat(closeprice-pRec).toFixed(decimal) ;
    if(result>0 || result<0)
    {return     result;   }
    else
    { return 0;}
}


//涨跌幅计算,四舍五入返回2位小数的百分值
function  changeRate(closeprice,pRec )
{   if(pRec<0.0001)
    {  return   0.00;  }
    else
    {return  ((closeprice-pRec)*100/pRec).toFixed(2);}

}
//时间戳转换成当地时间,字符串类型。
function timeStamptostr(timestamp,type) {

    var d = new Date(timestamp * 1000);   //yyyy-MM-dd hh:mm:ss
     return d.format(type);
}


//数据接口数据 加*1000得到图形能解释的参数
function getLocalTimeStamp(nS) {
    return new Date(parseInt(nS) * 1000).toGMTString().replace(/年|月/g, "-").replace(/日/g, " ");
}


//传入商品code  得到商品中文名

function getSymbolName(symbolCode)
{    var result=symbolCode;
    for(i=0;i<symbolJson.length;i++)
    {   if(symbolJson[i].SYMBOL_CODE==symbolCode )
       {
        result=symbolJson[i].SYMBOL_NAME;
        break;
      }
    }
    return result;
}


//传入交易所Code 得到  交易所中文名

function getExchangeName(exchCode)
{    var result=exchCode;
    for(i=0;i<exchangeJson.length;i++)
    {   if(exchangeJson[i].EXCH_CODE==exchCode )
       {
           result=exchangeJson[i].EXCH_NAME;
           break;
       }
    }
    return result;
}
//传入商品code_type  得到交易时间段

function getTradeSession(codeType)
{    var result="";
    for(i=0;i<codeTypeJson.length;i++)
    {
        if(codeTypeJson[i].CODE_TYPE==codeType )
      {
        result=codeTypeJson[i].TRADE_SESSION;
        break;
       }
    }
    return result;
}

//
function GetRandomNum(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    return (Min + Math.round(Rand * Range));
}

//时间格式化   alert(d.format("yyyy-MM-dd hh:mm:ss"));
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
//日期加上天数后的新日期.
function AddDays(date, days) {
    var nd = new Date(date);
    nd = nd.valueOf();
    nd = nd + days * 24 * 60 * 60 * 1000;
    nd = new Date(nd);
    //alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() + "日");
    var y = nd.getFullYear();
    var m = nd.getMonth() + 1;
    var d = nd.getDate();
    if (m <= 9) m = "0" + m;
    if (d <= 9) d = "0" + d;
    var cdate = y + "-" + m + "-" + d;
    return cdate;
}
//字符串转化为时间。
function stringtoTime(date1) {
    var dt = new Date(Date.parse(date1.replace(/-/g, "/")));
    return dt;
}

//获取对应商品的小数点位数
 function getDecimal(code)
{
    var result=2;
    var decimal=0;

    for(i=0;i<symbolJson.length;i++)
    {
     if(symbolJson[i].SYMBOL_CODE==code )
     {
         if(symbolJson[i].PRICE_SCALE !=null && symbolJson[i].PRICE_SCALE !="" )
         { decimal=symbolJson[i].PRICE_SCALE;}
        break;
     }
    }
    if(decimal==1)
    {  result=0;}
    else if(decimal==1000)
    { result=3; }
    else if(decimal==10000)
    { result=4; }

    return  result;
}

//跨域AJAX获取数据
function crossDomainAjax (url, successCallback) {

    // IE8 & 9 only Cross domain JSON GET request
    if ('XDomainRequest' in window && window.XDomainRequest !== null) {

        var xdr = new XDomainRequest(); // Use Microsoft XDR
        try{
            xdr.open('get', url);
            xdr.timeout = 20000;
            xdr.onload = function () {
                var dom  = new ActiveXObject('Microsoft.XMLDOM'),
                    JSON = $.parseJSON(xdr.responseText);

                dom.async = false;

                if (JSON == null || typeof (JSON) == 'undefined') {
                    JSON = $.parseJSON(data.firstChild.textContent);
                }

                successCallback(JSON); // internal function
            };
        }
        catch(ex){
            alert(ex.message);
        }
        xdr.onerror = function() {
            _result = false;
        };

        xdr.send();
    }

    // IE7 and lower can't do cross domain
    else if (navigator.userAgent.indexOf('MSIE') != -1 &&
        parseInt(navigator.userAgent.match(/MSIE ([\d.]+)/)[1], 10) < 8) {
        return false;
    }

    // Do normal jQuery AJAX for everything else
    else {
        $.ajax({
            url: url,
            cache: false,
            dataType: 'json',
            type: 'GET',
            async: false, // must be set to false
            success: function (data, success) {
                successCallback(data);
            }
        });
    }
}

