    CKEDITOR.replace( 'product_name_detail', {
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
    
    CKEDITOR.instances.product_name_detail.on('change', function() { 
    	content_detail = CKEDITOR.instances.product_name_detail.getData();
    });
    
    CKEDITOR.instances.product_name_detail.on('instanceReady', function() { 
    	content_detail = CKEDITOR.instances.product_name_detail.getData();
    });
     