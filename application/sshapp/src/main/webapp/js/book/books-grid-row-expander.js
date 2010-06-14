Ext.onReady(function(){

  var writer = new Ext.data.JsonWriter({
    encode: false   // <-- don't return encoded JSON -- causes Ext.Ajax#request to send data using jsonData config rather than HTTP params
});

  var store = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
      url: 'http://localhost:8089/sshapp/books/getBooks'
    }),
    reader: new Ext.data.JsonReader({
      root:'data'
    },
    [{name: 'oid'},
     {name: 'title'},
     {name: 'published'},
     {name: 'isbn'},
     {name: 'edition'},
     {name: 'pages'}
    ]),
    writer: writer
  });

  store.load();

  // row expander
    var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<br><p><b>ISBN10:</b> {isbn}</p><br>',
            '<p><b>ISBN13:</b> {title}</p><br>',
            '<p><b>Description:</b> {published}</p>'
        )
    });

    var gridBooks = new Ext.grid.GridPanel({
        store: store,
        cm: new Ext.grid.ColumnModel({
            defaults: {
                sortable: true,
                width: 200
            },
            columns: [
                expander,
                {header: "Title", dataIndex: 'title'},
                {header: "Publisher", dataIndex: 'published'}
            ]
        }),
        width: 430,
        height: 270,
        plugins: expander,
        title: 'ExtJS Books',
        renderTo: 'gridBooks'
    });

});