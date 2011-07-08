Ext.onReady(function() {

var marquee = new Ext.ux.Marquee({
  step: 4,
  interval: 30,
  containerCls: 'marquee-container',
  textCls: 'marquee-text',
  text: [
    'This is a simple example of the scrolling',
    'If you have any questions, write me - ivan.novakov [at] debug [dot] cz',
    'Enjoy!','中文捏']
});
marquee.init();

});
