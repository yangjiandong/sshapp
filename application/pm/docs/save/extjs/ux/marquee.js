/**
 * Simple class for horizontal scrolling of text in a box written for Ext Core 3
 *
 * @author Ivan Novakov <ivan.novakov@debug.cz>
 * @link http://www.debug.cz/examples/js/marquee/
 */

Ext.namespace('Ext.ux');

/**
 * @class Ext.ux.Marquee
 * @param {} config
 */
Ext.ux.Marquee = function(config) {
  Ext.apply(this, {
    // the scroll step in pixels
    step : 5,
    // the update interval
    interval : 100,
    // the CSS class of the container
    containerCls : 'undefined-container-class',
    // the CSS class of the text element
    textCls : 'undefined-text-class',
    // the element ID of the text element
    textElmId : 'mtext',
    text : [
      'undefined text'
    ]
  });

  Ext.apply(this, config);

  this.currentTextIndex = -1;
  this.textElm = null;
  this.currentTask = null;
  this.taskRunner = null;

  this.addEvents({
    beforetextupdate : true
  });
};

/**
 * @class Ext.ux.Marquee
 * @extends Ext.util.Observable
 */
Ext.extend(Ext.ux.Marquee, Ext.util.Observable, {

  _initDom : function() {
    var elm = Ext.DomHelper.append(document.body, {
      tag : 'div',
      cls : this.containerCls,
      children : [
        {
          tag : 'span',
          id : this.textElmId,
          cls : this.textCls,
          html : ' '
        }
      ]
    });
  },

  init : function() {
    this._initDom();
    this.textElm = this._getTextElm();
    var text = this._getCurrentText();

    this._resetTextElm();

    this.currentTask = {
      run : this.move,
      interval : this.interval,
      scope : this
    };

    this.taskRunner = new Ext.util.TaskRunner();
    this.taskRunner.start(this.currentTask);
  },

  move : function() {
    if (this.textElm.getRight() <= 0) {
      this.fireEvent('beforetextupdate');
      this._resetTextElm();
    }

    var left = this.textElm.getX() - this.step;
    this.textElm.setX(left);
  },

  _resetTextElm : function() {
    this.textElm.setX(this.textElm.parent().getRight());
    this.textElm.update(this._getNextText());
  },

  _getNextText : function() {
    this._incrementTextIndex();
    return this._getCurrentText();
  },

  _getCurrentText : function() {
    return this.text[this.currentTextIndex];
  },

  _incrementTextIndex : function() {
    if (this.currentTextIndex >= this.text.length - 1) {
      this.currentTextIndex = 0;
    } else {
      this.currentTextIndex++;
    }
  },

  _getTextElm : function() {
    return Ext.get(this.textElmId);
  }

});
