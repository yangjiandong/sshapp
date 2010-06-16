Ext.onReady(function() {

      Ext.BLANK_IMAGE_URL = "../img/s.gif";

      var Contact = Ext.data.Record.create([{
            name : 'id'
          }, {
            name : 'name',
            type : 'string'
          }, {
            name : 'phone',
            type : 'string'
          }, {
            name : 'email',
            type : 'string'
          }, {
            name : 'birthday',
            type : 'date',
            dateFormat : 'Y.m.d'
          }]);

      var proxy = new Ext.data.HttpProxy({
            api : {
              read : '../contact/getContacts',
              create : '../contact/create',
              update : '../contact/update',
              destroy : '../contact/delete'
            }
          });

      var reader = new Ext.data.JsonReader({
            totalProperty : 'total',
            successProperty : 'success',
            idProperty : 'id',
            root : 'data',
            messageProperty : 'message' // <-- New "messageProperty" meta-data
          }, Contact);

      // The new DataWriter component.
      var writer = new Ext.data.JsonWriter({
            encode : true,
            writeAllFields : false
          });

      // Typical Store collecting the Proxy, Reader and Writer together.
      var store = new Ext.data.Store({
        id : 'user',
        proxy : proxy,
        reader : reader,
        writer : writer, // <-- plug a DataWriter into the store just as you
                          // would a Reader
        autoSave : false
          // <-- false would delay executing create, update, destroy requests
          // until specifically told to do so with some [save] buton.
        });

      // read the data from simple array
      store.load();

      Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
            Ext.Msg.show({
                  title : '出错了!',
                  msg : '信息: '+res.message,
                  icon : Ext.MessageBox.ERROR,
                  buttons : Ext.Msg.OK
                });
          });

      var editor = new Ext.ux.grid.RowEditor({
            saveText : '保存'
          });

      // create grid
      var grid = new Ext.grid.GridPanel({
            store : store,
            columns : [{
                  header : "姓名",
                  width : 170,
                  sortable : true,
                  dataIndex : 'name',
                  editor : {
                    xtype : 'textfield',
                    allowBlank : false
                  }
                }, {
                  header : "电话号码",
                  width : 150,
                  sortable : true,
                  dataIndex : 'phone',
                  editor : {
                    xtype : 'textfield',
                    allowBlank : false
                  }
                }, {
                  header : "邮箱",
                  width : 150,
                  sortable : true,
                  dataIndex : 'email',
                  editor : {
                    xtype : 'textfield',
                    allowBlank : false
                  }
                }, {
                  header : "出生日期",
                  width : 100,
                  sortable : true,
                  dataIndex : 'birthday',
                  renderer : Ext.util.Format.dateRenderer('Y.m.d'),
                  editor : new Ext.form.DateField({
                        allowBlank : false,
                        format : 'Y.m.d',
                        maxValue : (new Date())
                      })
                }],
            plugins : [editor, Ext.ux.grid.DataDrop],
            title : 'My Contacts',
            height : 300,
            width : 610,
            frame : true,
            tbar : [{
                  iconCls : 'icon-user-add',
                  text : 'Add Contact',
                  handler : function() {
                    var e = new Contact({
                          name : 'New Guy',
                          phone : '(000) 000-0000',
                          email : 'new@loianetest.com',
                          birthday : '2000.01.01'
                        });
                    editor.stopEditing();
                    store.insert(0, e);
                    grid.getView().refresh();
                    grid.getSelectionModel().selectRow(0);
                    editor.startEditing(0);
                  }
                }, {
                  iconCls : 'icon-user-delete',
                  text : 'Remove Contact',
                  handler : function() {
                    editor.stopEditing();
                    var s = grid.getSelectionModel().getSelections();
                    for (var i = 0, r; r = s[i]; i++) {
                      store.remove(r);
                    }
                  }
                }, {
                  iconCls : 'icon-user-save',
                  text : 'Save All Modifications',
                  handler : function() {
                    store.save();
                  }
                }]
          });

      // render to DIV
      grid.render('crud-grid');
    });