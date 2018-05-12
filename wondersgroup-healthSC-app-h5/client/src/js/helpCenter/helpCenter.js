$(function() {
  var Accordion = function(el, multiple) {
    this.el = el || {};
    this.multiple = multiple || false;

    // Variables privadas
    var links = this.el.find('.question');
    // Evento
    links.on('touchstart', {
     el: this.el,
     multiple: this.multiple
    }, this.dropdown)
  }

  Accordion.prototype.dropdown = function(e) {
    // event.stopPropagation();
    // event.preventDefault();
    var $el = e.data.el;
    $this = $(this),
      $next = $this.next();

    $next.slideToggle();
    $this.parent().toggleClass('open');

    if (!e.data.multiple) {
      $el.find('.answer').not($next).slideUp().parent().removeClass('open');
    };
  }

  var accordion = new Accordion($('#accordion'), false);
});
