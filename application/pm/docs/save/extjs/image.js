Ext.onReady(function() {
      new Ext.Window({
            title : '人员信息',
            width : 500,
            height : 400,
            x : 400,
            y : 100,
            minimizable : true,
            maximizable : true,
            plain : true,
            items : [{
                  baseCls : 'x-plain',
                  layout : 'column',
                  style : 'padding:5px',
                  items : [{
                        baseCls : 'x-plain',
                        columnWidth : 0.6,
                        defaultType : 'textfield',
                        layout : 'form',
                        labelWidth : 65,
                        defaults : {
                          width : 160
                        },
                        items : [{
                              fieldLabel : '姓名'
                            }, {
                              fieldLabel : '年龄'
                            }, {
                              fieldLabel : '出生日期'
                            }, {
                              fieldLabel : '所在城市'
                            }, {
                              fieldLabel : '手机号码'
                            }, {
                              fieldLabel : '邮政编码'
                            }, {
                              fieldLabel : '性别'
                            }]
                      }, {
                        baseCls : 'x-plain',
                        columnWidth : 0.4,
                        defaultType : 'textfield',
                        layout : 'form',
                        labelWidth : 55,
                        items : [{
                              fieldLabel : '我的照片',
                              inputType : 'image',
                              width : 170,
                              height : 183
                            }]
                      }]
                }, {
                  style : 'padding:5px',
                  baseCls : 'x-plain',
                  defaultType : 'textfield',
                  layout : 'form',
                  defaults : {
                    width : 395
                  },
                  labelWidth : 67,
                  items : [{
                        fieldLabel : '身份证号码'
                      }, {
                        fieldLabel : '具体所在地'
                      }, {
                        fieldLabel : '当前的职位'
                      }]
                }],
            buttons : [{
                  text : '确定'
                }, {
                  text : '取消'
                }],
            listeners : {
              'show' : function(_win) {
                var img = _win.findByType('textfield')[7];
                img.getEl().dom.src = 'picture/mypicture.jpg';
              }
            }
          }).show();
    });