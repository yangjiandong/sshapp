Ext.QuickTips.init();
Ext.ns("N21.Other");

//Ext.ns.ImageField = Ext.extend(Ext.form.Field, {
//      autoCreate		: {
//        tag	: 'img'
//      },
//      setValue		: function(new_value) {
//        if (new_value == undefined || new_value == null) {
//          this.el.dom.src = CFG_PATH_ICONS + '/extlogo64.gif';
//        } else {
//          this.el.dom.src = CFG_PATH_ICONS + '/' + new_value;
//        }
//      },
//      initValue		: function() {
//        this.setValue(this.value);
//      },
//      initComponent	: function() {
//        Ext.apply(this, {
//        });
//        Ext.ns.ImageField.superclass.initComponent.apply(this);
//      }
//    });
//
//Ext.reg('image_field', Ext.ns.ImageField);

//http://www.extjs.com/forum/showthread.php?t=59623
Ext.ux.Image = Ext.extend(Ext.BoxComponent, {
  src : Ext.BLANK_IMAGE_URL,

  autoEl : {
    tag : 'img',
    cls : 'tng-managed-image',
    src : Ext.BLANK_IMAGE_URL
  },

  initComponent: function () {
    Ext.ux.Image.superclass.initComponent.apply(this, arguments);
  },

  onRender: function(){
    Ext.ux.Image.superclass.onRender.apply(this, arguments);

    if(!Ext.isEmpty(this.src) && (this.src !== Ext.BLANK_IMAGE_URL)){
      this.setSrc(this.src);
    }
    this.relayEvents(this.el,
      [
        "click", "dblclick", "mousedown", "mouseup", "mouseover",
        "mousemove", "mouseout", "keypress", "keydown", "keyup"
      ]
    );
  },

  setSrc: function (src) {
    this.el.dom.src = CFG_PATH_ICONS + '/' +src;
  }
});

Ext.reg('image', Ext.ux.Image);

N21.Other.DcLogin = Ext.extend(Ext.Window, {
      fields			: new Ext.util.MixedCollection(),
      authServerUrl	: null,
      authObj			: null, // on successfull authentication, this is
                  // filled
      // with session info returned by server(user full
      // name, authentication hash-code, etc)

      initComponent	: function() {
        this.fields.add("username", new Ext.form.TextField({
                  id			: 'userName',
                  allow_blank	: false,
                  blankText	: '请输入用户名',
                  value		: '',
                  fieldLabel	: L('/Login/UserName'),
                  width		: 150,
                  autoCreate	: {
                    tag				: "input",
                    // type : "text",
                    autocomplete	: "on"
                  }
                }));

        this.fields.add("password", new Ext.form.TextField({
                  id			: 'password',
                  allow_blank	: false,
                  value		: '',
                  inputType	: 'password',
                  fieldLabel	: L('/Login/Password'),
                  width		: 150
                }));
        this.fields.add("logoimg", new Ext.ux.Image({
                  id	: 'logoimg'
                  //,width     : 64
                  ,autoHeight:true
                  ,src   : "extlogo64.gif"
                }))

        Ext.apply(this, {
              title		: '用户登录?',
              width		: 300,
              autoHeight	: true,
              layout		: "form",
              closable	: false,
              closeAction	: 'hide',
              bodyStyle	: 'padding:10px;',
              plain		: true,
              modal		: true,
              resizable	: false,
              constrain	: true,
              items		: [this.fields.get("username"),
                  this.fields.get("password")
                  , this.fields.get("logoimg")
                  ],
              buttons		: [{
                    id		: 'signin',
                    // xtype : "button",
                    scope	: this,
                    text	: L('/Login/OK'),
                    handler	: this.onLogin
                  }],
              keys		: [{
                    key		: 13,
                    scope	: this,
                    fn		: this.onLogin
                  }]

            });
        N21.Other.DcLogin.superclass.initComponent.apply(this, arguments);
        this.addEvents('logonSuccess');
      },

      focusUserName	: function() {
        this.fields.get("username").focus(true, 500);
      },

      /**
       * Logon event processing.
       */
      onLogin			: function() {
        this.fields.get("logoimg").setSrc("extlogo64-anim.gif");
        Ext.Ajax.request({
              url			: this.authServerUrl + "?_p_action=login&_p_usr="
                  + this.fields.get("username").getValue() + "&_p_psw="
                  + this.fields.get("password").getValue(),
              callback	: this.afterOnlogin,
              scope		: this
            });
      },

      /**
       * Post logon processing. If authentication succesful hide the logon
       * window and load the main menu.
       */
      afterOnlogin	: function(o, s, r) {
        this.fields.get("logoimg").setSrc("extlogo64.gif");
        var respText = Ext.decode(r.responseText);
        if (respText.success) {
          this.fields.get("password").setRawValue("");
          this.authObj = new Object();
          // Add necessary properties to authObj ...
          this.fireEvent("logonSuccess", this.authObj);
        } else {
          if (!respText.success) {
            Ext.Msg.alert('用户登录出错', urldecode(respText.message));
          }
          this.fields.get("password").setRawValue("");
        }
      }
    });
// Ext.reg("DcLogin", N21.Other.DcLogin);
