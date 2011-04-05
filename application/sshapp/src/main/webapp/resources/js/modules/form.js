Ext.extend(demo.module,{
    init: function(){
        this.body = new Ext.form.FormPanel({
            labelWidth: 60, 
            border: false,
            bodyStyle:'padding:10px',
            layout:'column',
            items: [{
                columnWidth:.25,
                layout: 'form',
                border: false,
                items:[{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt1',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt3',
                    anchor:'95%'
                }]
            },{
                columnWidth:.25,
                layout: 'form',
                border: false,
                items:[{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt1',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt3',
                    anchor:'95%'
                }]
            },{
                columnWidth:.25,
                layout: 'form',
                border: false,
                items:[{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt1',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt3',
                    anchor:'95%'
                }]
            },{
                columnWidth:.25,
                layout: 'form',
                border: false,
                items:[{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt1',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt2',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '表单项',
                    name: 'txt3',
                    anchor:'95%'
                }]
            }],
            tbar: [{
                iconCls:'btn-save',
                text: '保存'
            },{
                iconCls:'btn-undo',
                text: '取消'
            }]
        });
        this.main.add(this.body);
        this.main.doLayout();  
    }
});