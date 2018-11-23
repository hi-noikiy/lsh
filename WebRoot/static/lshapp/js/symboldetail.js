//方法加载
$(function () {
    //第一次加载
    timelyPrice();//报价牌
    ChangeTimelyPrice();//实时更新报价牌
    SetTimelyChart(yesterdayPrice,exchCode);//自动画点
  //  alert(yesterdayPrice);
    MarketKLine("100","5");//K线图

});

//分钟切换
function ChooseKLine( zhizhen,limit,resolution){
    $(zhizhen).toggleClass("cruct");
    $(zhizhen).siblings(".cruct").removeClass("cruct");

    $("#hangqing_all .highcharts-container").hide();
    MarketKLine(limit,resolution);
}
//K线图
function MarketKLine(limit,resolution) {

    Highcharts.theme = {
        xAxis: {
            gridLineWidth: 1
        }
    };

    var Digit = getDecimal(symbolCode.toUpperCase()); //小数位数
    var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
    var dataAll = [];//所有历史数据数组
    var ave5=[];//五分钟均线
    var ave10=[];
    var ave30=[];
    var agent=0.0000;
    //历史数据路径
    var pathAll=histor+"?symbol="+encodeURIComponent(symbolCode)+"&limit="+limit+"&resolution="+resolution+"&codeType="+codeType+"&st=" + Math.random();
    // var pathAll=hangq+"histories.php?symbol="+encodeURIComponent(symbolCode)+"&limit="+limit+"&resolution="+resolution+"&codeType="+codeType+"&st=" + Math.random();

    $.getJSON(pathAll, function (data) {

        if (data.t.length == 0) {
            return;
        }
       /* alert(timeStamptostr( (data.t[0]),'yyyy-MM-dd hh:mm:ss'));
        alert(timeStamptostr( (data.t[ data.t.length-1]),'yyyy-MM-dd hh:mm:ss'));*/

        for (i = data.t.length-1; i >=0 ; i--)
        {//接口数据存入历史数据数组
            dataAll.push([
                Date.parse(getLocalTime(data.t[i])), // the date result.t[0]
                parseFloat(eval(data.o[i])), // open
                parseFloat(eval(data.h[i])), // high
                parseFloat(eval(data.l[i])), // low
                parseFloat(eval(data.c[i])) // close
            ]);
        }
        dataAll.sort(); //按字母先后排序

        for(i=0;i<dataAll.length;i++)
        {
            if (i>=4) {//填充 ave5 均线数据
                for (var j = i ; j >=i-4; j--)
                {
                    agent += dataAll[j][4];
                }
                ave5.push([
                    dataAll[i][0], // the date
                    agent / 5 // close
                ]);
                agent = 0.0000;

            } else {
                ave5.push([
                    dataAll[i][0], // the date
                    dataAll[i][4]
                ]);
            }

            if (i>=9) {//填充 ave10均线数据
                for (var j = i ; j >=i-9; j--)
                {
                    agent += dataAll[j][4];
                }
                ave10.push([
                    dataAll[i][0], // the date
                    agent / 10 // close
                ]);
                agent = 0.0000;

            } else {
                ave10.push([
                    dataAll[i][0], // the date
                    dataAll[i][4]
                ]);
            }

            if (i>=19) {//填充 ave30 均线数据
                for (var j = i ; j >=i-19; j--)
                {
                    agent += dataAll[j][4];
                }
                ave30.push([
                    dataAll[i][0], // the date
                    agent / 20 // close
                ]);
                agent = 0.0000;

            } else {
                ave30.push([
                    dataAll[i][0], // the date
                    dataAll[i][4]
                ]);
            }
        }
        //排序
        ave5.sort();
        ave10.sort();
        ave30.sort();

      //  alert(timeStamptostr( (dataAll[0][0]/1000),'yyyy-MM-dd hh:mm:ss'));小
      // alert(timeStamptostr( (dataAll[dataAll.length-1][0]/1000),'yyyy-MM-dd hh:mm:ss'));//最后的 就在最后

        dataAll.splice(0,(dataAll.length-81));
        ave5.splice(0,(ave5.length-81));
        ave10.splice(0,(ave10.length-81));
        ave30.splice(0,(ave30.length-81));

        Highcharts.setOptions({
            global: {
                useUTC: false
            },
            lang: {
                rangeSelectorZoom: "选择类别",
                resetZoom: "重置缩放",
                resetZoomTitle: "复位缩放比例1:1"

            }
        });

        //画图形
     $('#hangqing_all').highcharts('StockChart', {
            chart: {
                height: "410",
                width: "600",
                plotBorderWidth: 1,
               // backgroundColor:'#',
                plotBackgroundImage: backgroundurl+'/txzx_kline.png'

            },
            colors: ['#000000', '#0000ff', '#ff00ff', '#f7a35c', '#8085e9'],//线条颜色次序
            rangeSelector: {
                inputEnabled: false,
                enabled: false
            },

          exporting:{
              enabled:false
          },

         credits:{enabled:false},
            xAxis: {
             //tickInterval: 60,
             //   minPadding:0.1,
             // endOnTick:true,
             //  maxPadding:StopTim,
               tickPixelInterval: 128, //宽度
                labels: {
                    formatter: function () {//?
                        if (resolution == "1" || resolution == "5" || resolution == "15" || resolution == "30" || resolution == "60") {
                            return Highcharts.dateFormat('%m/%d %H:%M', this.value);
                        } else if (resolution == "D" ) {
                            return Highcharts.dateFormat('%y/%m/%d', this.value);
                        }
                    },
                    style: {
                        color: '#6D869F',
                        fontSize: '12'
                    },
                    rotation: 0,
                    x: 1
                }
            },
            yAxis: [{
                lineWidth: 0,
                labels: {
                    align: 'right',
                    x: 0,
                    y: 0,
                    formatter: function () {
                        return this.value.toFixed(Digit);
                    }
                },
                opposite: false//相反方向
            }
                    ],
            tooltip: {
                formatter: function () {
                    var chart = $('#hangqing_all').highcharts();
                    var s = Highcharts.dateFormat('<span style="font-size:10px"> %Y/%m/%d <br> %H:%M:%S</span>', this.x);
                    $.each(this.points, function (i, point) {
                        s += '<style> table tr td{ border:none; height: 14px;font: "Microsoft YaHei";}</style>   <table>  <tr> <td >开盘:</td> <td style="text-align: right"><b>' + this.point.options.open.toFixed(Digit)
                        + '</b></td> </tr>  <tr><td>最高:</td><td style="text-align: right"><b>' + this.point.options.high.toFixed(Digit)
                        + '</b></td></tr>   <tr><td >最低:</td><td style="text-align: right"><b>' + this.point.options.low.toFixed(Digit)
                        + '</b></td></tr>   <tr> <td>收盘:</td><td style="text-align: right"><b>' + this.point.options.close.toFixed(Digit)
                        + '</b></td></tr></table> ';
                        return false;
                    });
                    return s;
                },
                shared: true,
                useHTML: true,
                headerFormat: '<small>{point.key}</small><table>', //工具提示的标题
                pointFormat: '<tr><td style="color: {series.color}">{series.name}: </td>' +
                 '<td style="text-align: right"><b>{point.y} </b></td></tr>', //HTML的点的线在工具提示
                footerFormat: '</table>',
                valueDecimals: 2, //有多少位数显示在每个系列的y值
                xDateFormat: "时间:%Y/%m/%d  %H:%M:%S", //日期的格式在工具提示标提
                crosshairs: [{ //十字准线
                    color: '#b9b9b0'//颜色
                }, {
                    color: '#b9b9b0'
                }]
            },
            navigator: {
                enabled: false//关闭导航
            },
            scrollbar: {
                enabled: false//显示滚动条
            },
            plotOptions: {
               candlestick: {
                    color: '#6d9e81',
                    upColor: '#cc5444'
                },
              line:{
                 marker:{// hover中为鼠标放在某个点上的样式
                    states:
                     {
                       hover: {enabled:false },
                       select:{ enabled:true}
                     }
                   },
                 states:{
                     hover:{enabled:false}
                  }
                }
            },
            series: [{
                type:'candlestick',
                lineWidth:1,
                 data: dataAll
            } ,
                {
                    type: 'line',
                    lineWidth:0.8,
                    data: ave5
                }
                ,
                {
                    type: 'line',
                    lineWidth:0.8,
                    data: ave10
                }    ,
                {
                    type: 'line',
                    lineWidth:0.8,
                    data: ave30
                }

            ]
        });
    });
}


//--------------------------------------------------------------自动画点
var ChartTimer;
function SetTimelyChart( _yrice,_exchCode ) {
    Highcharts.theme = {
        xAxis: {
            gridLineWidth: 1
        }
    };
    var Digit = getDecimal(symbolCode.toUpperCase()); //小数位数
    var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
    Highcharts.setOptions({
        global: {
            useUTC: false
        },
        lang: {
            rangeSelectorZoom: '',
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            shortMonths: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            weekdays: ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
        }
    });

    // var hpath=hangq+"histories.php?symbol="+encodeURIComponent(symbolCode)+"&limit=288&resolution=5&codeType="+codeType+"&st=" + Math.random();
    var hpath=histor+"?symbol="+encodeURIComponent(symbolCode)+"&limit=288&resolution=5&codeType="+codeType+"&st=" + Math.random();
   // alert(1440);
    $.getJSON(hpath, function(data) {
      //  alert(data.t.length);
        if (data.t.length == 0) {
            //alert("图形暂无数据");
            return;
        }
        //获取数据条数要按时间段的 数据点数取，需优化
        var trade_time=getTradeSession(codeType); //交易时间段
        var trade_arry=trade_time.split(",");
        //起始时间点用
      /*      var start_str=trade_arry[0].substr(0,4);
        var stop_str=trade_arry[trade_arry.length-1].substr(5,4);
        */
        //scc
        //alert(trade_arry[0]);
        //起始时间点用
        	if(trade_arry[0] == '24x7')
        	{
        		var start_str =  '0500';
        		var stop_str =  '0500';
        	}else{
        		 var start_str=trade_arry[0].substr(0,4);
        	     var stop_str=trade_arry[trade_arry.length-1].substr(5,4);
        	}

        //scc end
        
        //间断点用
        var temp1=0;
        if(trade_arry.length>=2)
        {   temp1=1;
            var temp1_str=trade_arry[0].substr(5,4);
            var temp1_stop=trade_arry[1].substr(0,4);

        }


        var last_time=data.t[0];//历史数据中 最后一个点的时间搓, 每画一个点得传入新点的时间撮( 需按情况处理 )

        var pre_last_time=data.t[1];//历史数据中 当前倒数第二个点的时间撮, 数据点的区间按这个点的日期取。

        var pre_last_date=timeStamptostr(pre_last_time,"yyyy-MM-dd");//取最后一个点的日期
        var pre_start_str=pre_last_date.toString()+" "+start_str.substr(0,2)+":"+start_str.substr(2,2)+":00";//字符串，需转换成时间类型
        var pre_start_map=stringtoTime(pre_start_str).getTime();
        if(last_time==(pre_start_map/1000))
        {
            last_time=pre_last_time;//取前一个周期
        }

        var last_date=timeStamptostr(last_time,"yyyy-MM-dd");//取最后一个点的日期
        var newstart_date=last_date.toString()+" "+start_str.substr(0,2)+":"+start_str.substr(2,2)+":00";//开始时间字符串，需转换成时间类型

        var newstop_date=last_date.toString()+" "+stop_str.substr(0,2)+":"+stop_str.substr(2,2)+":00";


        var new_start=stringtoTime(newstart_date);//第一个数据点的时间

        var new_stop=stringtoTime(newstop_date);

        var start_stamp=new_start.getTime();//第一个数据点的初始时间撮,

        var stop_stamp=new_stop.getTime();//最后坐标的初始时间撮，经多重判断后会赋值给StopTim，作为最终坐标


        var StartTim,StopTim;//数据抽的起 始坐标

    /*   alert(timeStamptostr( (data.t[0]),'yyyy-MM-dd hh:mm:ss'));
        alert(timeStamptostr( last_time,'yyyy-MM-dd hh:mm:ss'));*/

        var Price = [];//历史数据数组
        var pline = [];//昨收价数据数组
        for (i = 0; i < data.t.length; i++)
        {
            if(data.t[i]>=(start_stamp/1000) )
            {
                Price.push([
                    Date.parse(getLocalTime(data.t[i])), // the date result.t[0]
                    parseFloat(eval(data.c[i])) // close
                ]);
            }

        }//当天接口历史数据存入数组
        if(Price.length>1)//当天行情有数据
        {
            if(stop_stamp>start_stamp)
            {
                StartTim =start_stamp; //开盘时间
                StopTim =stop_stamp; //休市时间
            }
            else
            {
                StartTim =start_stamp; //开盘时间
                StopTim =stop_stamp +(24 * 60 * 60 * 1000); //休市时间后推一天
            }
        }
        else//前一天接口历史数据存入数组
        {
            if(stop_stamp>start_stamp)
            {
                StartTim =start_stamp-(24 * 60 * 60 * 1000); //开盘时间
                StopTim =stop_stamp-(24 * 60 * 60 * 1000); //前一个日期 休市时间
            }
            else
            {
                StartTim =start_stamp-(24 * 60 * 60 * 1000); //前一个日期的开盘时间点
                StopTim =stop_stamp; //休市时间后推一天
            }
            Price.length=0;//清空数组
            for (i = 0; i < data.t.length; i++) {
                if(data.t[i]>=StartTim/1000)
                { Price.push([
                    Date.parse(getLocalTime(data.t[i])), // the date result.t[0]
                    parseFloat(eval(data.c[i])) // close
                ]);
                }

            }//接口数据存入历史数据数组

        }
        Price.sort();//按时间排序 一定要。

       // alert(timeStamptostr( (StartTim/1000),'yyyy-MM-dd hh:mm:ss'));
       // alert(timeStamptostr( StopTim/1000,'yyyy-MM-dd hh:mm:ss'));
        /* alert(timeStamptostr( (Price[0][0]/1000),'yyyy-MM-dd hh:mm:ss'));
       alert(timeStamptostr( Price[Price.length-1][0]/1000,'yyyy-MM-dd hh:mm:ss'));*/
        var ptime=StartTim;
        var dex=((StopTim-StartTim)/(5*60*1000))+1;//昨收价参考线的数据点数量
        var pdata=0.0000;

        //获取昨收价
       pdata=parseFloat(_yrice);

        if(pdata<0.0002)
        {
            pdata=Price[0][1];
        }

         //间断点时间戳
       if(temp1==1)
       { if(_exchCode !='SHFE')
          {//除SHFE交易所外其余都为结束的日期加间断点时间

              var temp1_str_time=timeStamptostr( (StopTim/1000),'yyyy-MM-dd')+" "+temp1_str.substr(0,2)+":"+temp1_str.substr(2,2)+":00";//字符串，需转换成时间类型
              var temp1_str_map=stringtoTime(temp1_str_time).getTime();

              var temp1_stop_time=timeStamptostr( (StopTim/1000),'yyyy-MM-dd')+" "+temp1_stop.substr(0,2)+":"+temp1_stop.substr(2,2)+":00";//字符串，需转换成时间类型
              var temp1_stop_map=stringtoTime(temp1_stop_time).getTime();
          }
          else
          {
              var temp1_str_time=timeStamptostr( (StartTim/1000),'yyyy-MM-dd')+" "+temp1_str.substr(0,2)+":"+temp1_str.substr(2,2)+":00";//字符串，需转换成时间类型
              var temp1_str_map=stringtoTime(temp1_str_time).getTime();

              var temp1_stop_time=timeStamptostr( (StartTim/1000),'yyyy-MM-dd')+" "+temp1_stop.substr(0,2)+":"+temp1_stop.substr(2,2)+":00";//字符串，需转换成时间类型
              var temp1_stop_map=stringtoTime(temp1_stop_time).getTime();
          }

       }
        var prec=0;
        for (i = 0; i < dex; i++)
        {
               ptime=StartTim+i*5*60*1000
              if(temp1==1)
              {
                  if(ptime>temp1_str_map && ptime<temp1_stop_map)
                  {
                      continue;
                  }
              }
                pline.push([
                    ptime, // the date result.t[0]
                    pdata // close
                ]);
            prec=pdata;
        }//昨收价存入数组

        // Create the chart
        $('#hangqing_min').highcharts('StockChart', {
            navigator: { enabled: false },
            scrollbar: { enabled: false },
            rangeSelector: { enabled: false },
            credits: {
                text: ''
            },
           // colors: [ '#0000ff', '#ff00ff', '#f7a35c', '#8085e9'],//线条颜色次序
            chart: {
                height: "410",
                width: "600",
                plotBorderWidth: 1,
                plotBackgroundImage: backgroundurl+'/txzx_timely.png',
                events: {
                    load: function () {
                        var Chart = $('#hangqing_min').highcharts();
                        var s0 = Chart.series[0];
                        var loadData = function () {

                            // var jpath=hangq+"histories.php?symbol="+encodeURIComponent(symbolCode)+"&limit=1&resolution=5&codeType="+codeType+"&st=" + Math.random();
                            var jpath=histor+"?symbol="+encodeURIComponent(symbolCode)+"&limit=1&resolution=5&codeType="+codeType+"&st=" + Math.random();
                           // var jpath=hangq+"getQuote.php?exchName="+exchCode+"&symbol="+symbolCode+"&st=" + Math.random();
                            $.getJSON(jpath , function (data) {
                                if (data.t.length == 0) {
                                   // alert("无数据,请检查网络，重新刷新。");
                                    return;
                                }
                                var sign=0;
                                var today=new Date();
                               if ( today.getTime()> StopTim) {clearInterval(ChartTimer);}//当前时间大于停盘时间，停止刷新
                               // alert('异步画图2');

                               for (i=data.t.length-1;i>=0;i--)
                                {
                                   if(data.t[i]>=last_time &&data.t[i]<last_time+(7* 60))
                                    {
                                    var x = Date.parse(getLocalTime(data.t[i])), y1 = parseFloat(eval(data.c[i]));
                                    s0.addPoint([x, y1], false, false);

                                 // if(data.t[i]>last_time)
                                    //{
                                     Chart.redraw();
                                   // }

                                     last_time=data.t[i];
                                        sign++;
                                    }

                                }
                                     // if(sign<1)
                                     // {
                                         // alert("网络故障，请重新刷新");
                                     // }
                            })
                        }
                        ChartTimer = setInterval(loadData, 10000);
                    }
                }
            },
            xAxis: {
                type:'datetime',
                //gridLineWidth:1,
               min: StartTim, //x轴起始值，要参数
               max: StopTim, //x轴结束值，要参数

               // minPadding: 66,
                //endOnTick:true,
               // maxPadding:StopTim,
             //   minPadding: 66,
                dateTimeLabelFormats: {
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%m/%d',
                    week: '%m/%d',
                    month: '%Y/%m',
                    year: '%Y'
                },

              tickPixelInterval: 120,//宽度
                labels: {
                    formatter: function () {
                            return Highcharts.dateFormat('%H:%M', this.value);
                    }
                }
            },
            yAxis: [{
                lineWidth: 0,
                labels: {
                    align: 'right',
                  /*  x: 0,
                    y: 0,*/
                    formatter: function () {
                        if(this.value==prec){
                            return '<span style="color:#333333;">'+this.value.toFixed(Digit)+'</span>'
                        }else{
                            return (this.value > prec ? '<span style="color:#ff0000">'+this.value.toFixed(Digit)+'</span>' : '<span style="color:#1a700e">'+this.value.toFixed(Digit)+'</span>' );
                        }

                    }
                },
                plotLines : [{
                    value :prec,
                    color : '#ff5400',
                    dashStyle : 'shortdash',
                    width : 1,
                    label : {
                        text : ''
                    },
                    zIndex:2
                }],
                opposite: false//相反方向
            }
            ],
            plotOptions: {
                line:{
                    marker:{// hover中为鼠标放在某个点上的样式
                        states:
                        {
                            hover: {enabled:true },
                            select:{ enabled:true}
                        }
                    },
                    states:{
                        hover:{enabled:true ,
                            lineWidth:1
                               }
                    }
                },
                spline:{
                    enableMouseTracking:false,
                    states:{
                        hover:{enabled:false
                           // lineWidth:1
                        }
                    }
                }
            },
          //  tooltip: false,
            tooltip: {
                pointFormat: '<br/><span style="color:#333;">{series.name}</span>:<b>{point.y}</b><br/>',
                valueDecimals: Digit, //有多少位数显示在每个系列的y值
                shared:false,
                xDateFormat: "时间:%Y-%m-%d  %H:%M:%S",//日期的格式在工具提示标提
                borderColor: '#30527e'
               // shadow: false
            },

            series: [{
                name: '当前价',
                type:'line',
                data: Price,
                color: '#30527e',
                lineWidth: 1,
                yAxis: 0

            },
                {
                    name: '昨收价',
                    type: 'spline',
                    data: pline,
                    color: '#ffffff',
                    tooltip:{  formatter: function () {
                        var chart = $('#hangqing_min').highcharts();
                        var s = Highcharts.dateFormat('<span style="font-size:10px"> %Y-%m-%d  %H:%M:%S</span>', this.x);
                        return s;
                     }
                    },
                    lineWidth: 0.5

                }]
        });

    })
}

//报价牌
function timelyPrice()
{
    // var timelypath=hangq+"symbol.php?exchName="+exchCode+"&symbol="+encodeURIComponent(symbolCode)+"&st=" + Math.random();
    var symbo_code=encodeURIComponent(symbolCode);
    var timelypath=symbol+"?exchName="+exchCode+"&symbol="+symbo_code+"&st=" + Math.random();
    var pricehtml="";
    var infohtml="";
    var _decimal=getDecimal(symbo_code);//小数位数

    //  crossDomainAjax(timelypath, function (data) {
    $.getJSON(timelypath, function (data){
        if (data.o.length == 0) {
            $("#timely_price").html("<center> <font color='red' size=2>暂无数据，请刷新！</font></center>");
            return;
        }

        if(symbo_code=="1A0001"||symbo_code=="2A01")
        {
            if(data.c[0]>=data.p[0])
            {
                pricehtml="<span class='red_color'>"+parseFloat(data.c[0]).toFixed(_decimal)+"</span>";
            }else
            {
                pricehtml="<span class='green_color'>"+parseFloat(data.c[0]).toFixed(_decimal)+"</span>";
            }

        }
        else{
            if(data.b[0]>=data.p[0])
            {
                pricehtml="<span class='red_color'>"+parseFloat(data.b[0]).toFixed(_decimal)+"</span>";
            }else
            {
                pricehtml="<span class='green_color'>"+parseFloat(data.b[0]).toFixed(_decimal)+"</span>";
            }
            pricehtml +="/";

            if(data.se[0]>=data.p[0])
            {
                pricehtml+="<span class='red_color'>"+parseFloat(data.se[0]).toFixed(_decimal)+"</span><br />";
            }else
            {
                pricehtml+="<span class='green_color'>"+parseFloat(data.se[0]).toFixed(_decimal)+"</span><br />";
            }
        }


        var dif=difference(data.c[0], data.p[0],_decimal);
        if(dif>=0.00)
        {
            pricehtml +=  "<span class='part2_ft_red'>"+dif + "("+changeRate(data.c[0], data.p[0])+ "%)</span>";
        }
        else
        {
            pricehtml +=  "<span class='part2_ft_green'>"+dif + "("+changeRate(data.c[0], data.p[0])+ "%)</span>";
        }

        infohtml=  "  <ul>" ;//今开等信息
        if(data.o[0]>=data.p[0] )
        {
            infohtml+=  "<li>今开:<span class='red_tab'>"+parseFloat(data.o[0]).toFixed(_decimal)+"</span></li>" ;
        }else{
            infohtml+=  "<li>今开:<span class='green_tab'>"+parseFloat(data.o[0]).toFixed(_decimal)+"</span></li>" ;
        }
        if(data.h[0]>=data.p[0] )
        {
            infohtml+=  "<li>最高:<span  class='red_tab'>"+parseFloat(data.h[0]).toFixed(_decimal)+"</span></li>";
        }else{
            infohtml+=  "<li>最高:<span  class='green_tab'>"+parseFloat(data.h[0]).toFixed(_decimal)+"</span></li>";
        }
        infohtml+=  " <li>昨收:<span >"+parseFloat(data.p[0]).toFixed(_decimal) +"</span></li>" ;
        if(data.l[0]>=data.p[0] )
        {
            infohtml+=  "<li>最低:<span class='red_tab'>"+parseFloat(data.l[0]).toFixed(_decimal)+"</span></li>";
        }else{
            infohtml+=  "<li>最低:<span class='green_tab'>"+parseFloat(data.l[0]).toFixed(_decimal)+"</span></li>";
        }
        infohtml+=  " </ul>";

        $("#timely_price").html(pricehtml);
        $("#timely_price_info").html(infohtml);

    })
}

//实时更新牌价
function ChangeTimelyPrice()
{
    var timer=setInterval(function()
    {
        // var timelypath=hangq+"symbol.php?exchName="+exchCode+"&symbol="+encodeURIComponent(symbolCode)+"&st=" + Math.random();
        var symbo_code=encodeURIComponent(symbolCode);
        var timelypath=symbol+"?exchName="+exchCode+"&symbol="+symbo_code+"&st=" + Math.random();
        var _decimal=getDecimal(symbo_code);//小数位数
        $.getJSON(timelypath,function(data){
            if (data.o.length == 0) {
                //alert("无新数据");
                return;
            }
            var pricehtml="";
            var buy_price=parseFloat(data.b[0]).toFixed(_decimal);
            var sale_price=parseFloat(data.se[0]).toFixed(_decimal);
            var y_price=parseFloat(data.p[0]).toFixed(_decimal);
            var h_price=parseFloat(data.h[0]).toFixed(_decimal);
            var l_price=parseFloat(data.l[0]).toFixed(_decimal);

            var old_high=$("#timely_price_info").children().children(":eq(1)").children().text();
            var old_high_price=parseFloat(old_high).toFixed(_decimal);

            var old_low=$("#timely_price_info").children().children(":eq(3)").children().text();
            var old_low_price=parseFloat(old_low).toFixed(_decimal);

            var bs=$("#timely_price span:eq(0)").text().trim();
            var old_buy=parseFloat(bs).toFixed(_decimal);
            var current_price=parseFloat(data.c[0]).toFixed(_decimal);
            if(symbo_code=="1A0001"||symbo_code=="2A01"){
                buy_price=current_price;
            }

            if(old_buy != buy_price){
                if(symbo_code=="1A0001"||symbo_code=="2A01") {
                    if(buy_price>=y_price)
                    {
                        pricehtml="<span class='red_color'>"+buy_price+"</span>";
                    }else
                    {
                        pricehtml="<span class='green_color'>"+buy_price+"</span>";
                    }
                }else{
                    if(buy_price>=y_price)
                    {
                        pricehtml="<span class='red_color'>"+buy_price+"</span>";
                    }else
                    {
                        pricehtml="<span class='green_color'>"+buy_price+"</span>";
                    }
                    pricehtml +="/";

                    if(sale_price>=y_price)
                    {
                        pricehtml+="<span class='red_color'>"+sale_price+"</span><br />";
                    }else
                    {
                        pricehtml+="<span class='green_color'>"+sale_price+"</span><br />";
                    }
                }
                var dif=difference(data.c[0], data.p[0],_decimal);
                if(dif>=0.00)
                {
                    pricehtml +=  "<span class='part2_ft_red'>"+dif + "("+changeRate(data.c[0], data.p[0])+ "%)</span>";
                }
                else
                {
                    pricehtml +=  "<span class='part2_ft_green'>"+dif + "("+changeRate(data.c[0], data.p[0])+ "%)</span>";
                }
//alert(pricehtml);
              $("#timely_price").html(pricehtml);
            }


            if(old_high_price!=h_price)
            {
                $("#timely_price_info").children().children(":eq(1)").children().text(h_price.toString());
               if(h_price>=y_price){
                   $("#timely_price_info").children().children(":eq(1)").children().removeClass("green_tab").addClass("red_tab");

               }else{
                 $("#timely_price_info").children().children(":eq(1)").children().removeClass("red_tab").addClass("green_tab");
               }
            }

            if(old_low_price!=l_price)
            {
                $("#timely_price_info").children().children(":eq(3)").children().text(l_price.toString());
                if(l_price>=y_price){
                    $("#timely_price_info").children().children(":eq(3)").children().removeClass("green_tab").addClass("red_tab");

                }else{
                    $("#timely_price_info").children().children(":eq(3)").children().removeClass("red_tab").addClass("green_tab");
                }
            }

        });

    },1000*5);

}


//获取昨收价
function  getYprice(timelypath,Digit)
{
    var yprice=0.0000;
    $.getJSON(timelypath, function (data)
    {   var yprice=0.0000;
        if (data.o.length > 0)
        {
            if(parseFloat(data.p[0]).toFixed(Digit)>0.01)
            { yprice=parseFloat(data.p[0]).toFixed(Digit);  }
        }
        return  yprice;
    });
   return yprice;
}


 function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toGMTString().replace(/年|月/g, "-").replace(/日/g, " ");
}
