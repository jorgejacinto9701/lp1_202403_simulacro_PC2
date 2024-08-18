<!DOCTYPE html>
<html lang="esS" >
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/dataTables.bootstrap.min.css"/>
<link rel="stylesheet" href="css/bootstrapValidator.css"/>

<title>Sistemas - Jorge Jacinto Gutarra</title>
</head>
<body>
<jsp:include page="intranetCabecera.jsp" />
	<div class="container" style="margin-top: 4%">
	<h4>Registra Alumno</h4>
	
	<form id="id_form" action=" " >		
		<div class="row" style="margin-top: 4%">
			<div class="form-group col-md-6">
				<label class="control-label" for="id_nombre">Nombres</label>
				<input class="form-control" type="text" id="id_nombre" name="nombres" placeholder="Ingrese el nombre">	
			</div>
			<div class="form-group col-md-6">
				<label class="control-label" for="id_apellido">Apellidos</label>
				<input class="form-control" type="text" id="id_apellido" name="apellidos" placeholder="Ingrese el apellido">	
			</div>
		</div>
		<div class="row" style="margin-top: 2%">
			<div class="form-group col-md-3">
				<label class="control-label" for="id_telefono">Teléfono</label>
				<input class="form-control" type="text" id="id_telefono" name="telefono" placeholder="Ingrese el teléfono">	
			</div>
			<div class="form-group col-md-3">
				<label class="control-label" for="id_dni">DNI</label>
				<input class="form-control" type="text" id="id_dni" name="dni" placeholder="Ingrese el DNI">	
			</div>
			<div class="form-group col-md-6">
				<label class="control-label" for="id_correo">Correo</label>
				<input class="form-control" type="text" id="id_correo" name="correo" placeholder="Ingrese el correo">	
			</div>
		</div>
		<div class="row" style="margin-top: 2%">
			<div class="form-group col-md-6">
				<label class="control-label" for="id_fecha">Fecha Nacimiento</label>
				<input class="form-control" type="date" id="id_fecha" name="fechaNacimiento">	
			</div>
			<div class="form-group col-md-6">
				<label class="control-label" for="id_pais"> País </label> 
				<select	class="form-control" id="id_pais" name="pais" >
					<option value=" ">[Seleccione]</option>
				</select>
			</div>
		</div>
		<div class="row"  align="center" style="margin-top: 2%">
			<div class="form-group col-md-12">
					<button type="button" class="btn btn-primary" id="id_btn_registrar">Crea Alumno</button>
			</div>
		</div>
	</form>

</div>


<script type="text/javascript">

$(document)
.ready(function(){
	$('#id_form').bootstrapValidator(
                {
                    message : 'This value is not valid',
                    feedbackIcons : {
                        valid : 'glyphicon glyphicon-ok',
                        invalid : 'glyphicon glyphicon-remove',
                        validating : 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        nombres: {
                        	selector: "#id_nombre",
                            validators: {
                                notEmpty: {
                                    message: 'Los Nombres son requeridos'
                                },
                            }
                        },
                        apellidos: {
                        	selector: "#id_apellido",
                            validators: {
                                notEmpty: {
                                    message: 'Los Apellidos son requeridos'
                                },
                            }
                        },
                        telefono:{
                            selector: "#id_telefono",
                            validators:{
                                notEmpty: {
                                    message: 'El tel&eacute;fono es obligatorio'
                                },
                                regexp: {
                                    regexp: /^9[0-9]{8}$/,
                                    message: 'El tel&eacute;fono es de 9 d&iacute;gitos y empieza con 9'
                                }
                            }
                        },
                        dni:{
                            selector: "#id_dni",
                            validators:{
                                notEmpty: {
                                    message: 'El DNI es obligatorio'
                                },
                                regexp: {
                                    regexp: /^[0-9]{8}$/,
                                    message: 'El DNI es 8 d&iacute;gitos'
                                },
                                remote :{
                                	delay   : 1000,
                                	url     : 'validaRemoteRegistraAlumnoCampoDNIServlet',
                                	message : 'El DNI ya existe'
                                }
                            }
                        },
                        correo: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe ingresar un correo v&aacute;lido'
                                },
                                emailAddress: {
                        			message: 'Debe tener formato de correo'
                       			 },
                            }
                        },
                        fechaNacimiento: {
                            selector : "#id_fecha",
                            validators : {
                                notEmpty : {
                                    message : 'La fecha de nacimiento es requerida'
                                },
                                remote :{
                                	delay   : 1000,
                                	url     : 'validaRemoteRegistraAlumnoCampoFechaNacimientoServlet',
                                	message : 'No es mayor de Edad'
                                }
                            }
                        },
                        pais: {
                            validators: {
                                notEmpty: {
                                    message: 'Debe seleccionar un pa&iacute;s'
                                }
                            }
                        }
                    }
                });
    });
    
$("#id_btn_registrar").click(function() {
    var validator = $('#id_form').data('bootstrapValidator');
    validator.validate();
    
    if (validator.isValid()) {
        $.ajax({
            type: "POST",
            url: "registraAlumno",
            data: $('#id_form').serialize(),
            success: function(data){
                mostrarMensaje(data.mensaje);
                limpiarFormulario();
                validator.resetForm();
            },
            error: function(){
                mostrarMensaje(MSG_ERROR);
            }
        });
    }
   
});


$.getJSON("cargaPais", {}, function (data){
	$.each(data, function(index, item){
		$("#id_pais").append("<option value=" +  item.idPais +" >" +  item.nombre + "</option>");
	});	
});		

</script>
	
</body>
</html>







