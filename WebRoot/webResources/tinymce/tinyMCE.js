tinymce.init({
    selector: "textarea#CONTENTT",
    relative_urls : false,
    forced_root_block:"",
    language : "zh_CN",
           plugins: [
            "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker textcolor jbimages preview table contextmenu directionality emoticons template textcolor paste fullpage textcolor searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking"
     //       "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking"
//             "table contextmenu directionality emoticons template textcolor paste fullpage textcolor"
        ],

        toolbar1: "jbimages preview undo redo | cut copy paste | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect | forecolor backcolor | table code ",
//        toolbar2: " searchreplace | bullist numlist | outdent indent blockquote | link unlink anchor image media code | inserttime preview | forecolor backcolor",
//       toolbar3: "table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",
    setup : function(ed) {
      ed.on('keydown', function(e) {
          		 
			 $("#labelBottomDiv").css("display","");
		 
      });
   } ,
   autosave_ask_before_unload: false,
        menubar: false
       // toolbar_items_size: 'small'
 });
function eventHandle(event)  
{  
    if(event.type == 'click' || event.type == 'keyup'){
    	alert(111);

    }  
} 