(function($) {

  $('*[data-timepicker]').attr('autocomplete','off').keydown(function(e){

    var inputValue = $(this).val();
    if( (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8){


      if(inputValue.length == 0){
        if(e.keyCode > 49){
          e.preventDefault();
          $(this).val(2);
        }
      }


      else if(inputValue.length == 1 && e.keyCode != 8){
        e.preventDefault();
        if( e.keyCode > 50 ){
          $(this).val(inputValue + '3:');
        }
        else{
          $(this).val(inputValue + String.fromCharCode(e.keyCode) + ':');
        }
      }

      else if(inputValue.length == 2 && e.keyCode != 8){
        e.preventDefault();
        if( e.keyCode > 52 ){
          $(this).val(inputValue + ':5');
        }
        else{
          $(this).val(inputValue + ':' + String.fromCharCode(e.keyCode));
        }
      }

      else if(inputValue.length == 3 && e.keyCode != 8){
        if( e.keyCode > 52 ){
          e.preventDefault();
          $(this).val( inputValue + '5' );
        }
      }

      else if(inputValue.length > 4 && e.keyCode != 8){
        e.preventDefault();
        return false;
      }
    }

    else{
      e.preventDefault();
      return false;
    }
  });

})(jQuery);
