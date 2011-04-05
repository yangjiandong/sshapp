Ext.extend(demo.module,{
    init: function(){
        this.ds = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                method: 'GET',
                url: 'resources/data/grid.txt'
            }),
            reader: new Ext.data.JsonReader({
                root: 'rows',
                totalProperty: 'total',
                fields: ['Name','Sex','Phone','Email']
            })
    });
    this.body = new Ext.grid.GridPanel({
        store: this.ds,
        stripeRows: true,
            loadMask: true,
            border: false,
            autoExpandColumn: 'Email',
            columns: [
                new Ext.grid.RowNumberer(),
                {header: '姓名', width: 80, sortable: true, dataIndex: 'Name'},
                {header: '性别', width: 50, sortable: true, dataIndex: 'Sex',
                    renderer: function(value){
                        return value? '男':'女';
                    }
                },
                {header: '电话', width: 100, sortable: true, dataIndex: 'Phone'},
                {id:'Email', header: 'Email', width: 100, sortable: true, dataIndex: 'Email'}
            ],
            bbar: new Ext.PagingToolbar({
                pageSize: 15,
                store: this.ds,
                displayInfo: true
            })
        });
        this.main.add(this.body);
        this.main.doLayout();

        this.ds.load({
            params: {start:0, limit:15 }
        });
    }
});