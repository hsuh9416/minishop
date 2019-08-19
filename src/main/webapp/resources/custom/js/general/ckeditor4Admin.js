var content_detail='';

	CKEDITOR.replace( 'editor_admin', {
        width:'100%',
        height:'300px',
        filebrowserImageUploadUrl: '/minishop/admin/product/productImgUpload.do', 
        
    });
           
    CKEDITOR.on('dialogDefinition', function( ev ){
        var dialogName = ev.data.name;
        var dialogDefinition = ev.data.definition;
      
        switch (dialogName) {
            case 'image': 
                dialogDefinition.removeContents('Link');
                dialogDefinition.removeContents('advanced');
                break;
        }
    });
    
    CKEDITOR.instances.editor_admin.on('change', function() { 
    	content_detail = CKEDITOR.instances.editor_admin.getData();
    });
    
    CKEDITOR.instances.editor_admin.on('instanceReady', function() { 
    	content_detail = CKEDITOR.instances.editor_admin.getData();
    });
     