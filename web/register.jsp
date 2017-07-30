<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="css/materialize.css" type="text/css"/>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/angular.js"></script>
<script type="text/javascript" src="js/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-materialize/0.2.2/angular-materialize.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<body>

    <div class="col s12 m6 l6">
        <div class="card-panel">
            <h4 class="header2" >Registrar Usuario</h4>
        </div>
    </div>
    <form class="col s12" action="Registro" method="post">
        <div class="row">
            <div class="input-field col s4 offset-s2">
                <i class="material-icons prefix">account_circle</i>
                <input id="primer_nombre" type="text" class="validate" name="primer_nombre">
                <label for="primer_nombre">Primer Nombre</label>
            </div>
            <div class="input-field col s4">
                <i class="material-icons prefix">account_circle</i>
                <input id="segundo_nombre" type="text" class="validate" name="segundo_nombre">
                <label for="segundo_nombre">Segundo Nombre</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s4 offset-s2">
                <i class="material-icons prefix">account_circle</i>
                <input id="primer_apellido" type="text" class="validate" name="primer_apellido">
                <label for="primer_apellido">Primer Apellido</label>
            </div>

            <div class="input-field col s4">
                <i class="material-icons prefix">account_circle</i>
                <input id="segundo_apellido" type="text" class="validate" name="segundo_apellido">
                <label for="segundo_apellido">Segundo Apellido</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s4 offset-s2">
                <i class="material-icons prefix">account_circle</i>
                <input id="cedula" type="tel" class="validate" name="cedula">
                <label for="cedula">Cedula</label>
            </div>

            <div class="input-field col s4">
                <i class="material-icons prefix">phone</i>
                <input id="telefono" type="tel" class="validate" name="telefono">
                <label for="telefono">Telefono</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s4 offset-s2">
                <i class="material-icons prefix">location_on</i>
                <input id="direccion" type="tel" class="validate" name="direccion">
                <label for="direccion">direccion</label>
            </div>
            <div class="input-field col s4">
                <i class="material-icons prefix">schedule</i>
                <input id="nacimiento" type="date" class="datepicker" name="nacimiento">
                <label for="nacimiento">Fecha nacimiento</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s4 offset-s4">
                <i class="material-icons prefix">email</i>
                <input id="email" type="email" class="validate" name="email">
                <label for="email" data-error="wrong" data-success="right">Email</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s4 offset-s2">
                <i class="material-icons prefix">account_circle</i>
                <input id="usuario" type="text" class="validate" name="usuario">
                <label for="usuario">Usuario</label>
            </div>
            <div class="input-field col s4">
                <i class="material-icons prefix">lock</i>
                <input id="clave" type="password" class="validate" name="clave">
                <label for="clave">Clave</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s4 offset-s4">
                <i class="material-icons prefix">work</i> 
                <select name="repeatSelect" id="repeatSelect">
                    
                </select>
                <label>Rol</label>
            </div>
        </div>
        <div class="row">
            <div class="col s2 offset-s2">
                <button class="btn waves-effect waves-light" type="submit" name="registrar" value="btnRegistrar">Registrar
                    <i class="material-icons right">send</i>
                </button>
            </div>
            <div style="color: #FF0000;">${errorMessage}</div>
        </div>
        <br>
    </form>


    <script>
        $.get("Registro", function(result) {
                    $.each(result, function() {
                        $("#repeatSelect").append($("<option />").val(this).text(this));
                    });
                    
                    $(document).ready(function () {
                    $('select').material_select();
                    $('.datepicker').pickadate({
                        selectMonths: true, // Creates a dropdown to control month
                        selectYears: 400 // Creates a dropdown of 15 years to control year
                    });
                    
                });
                });
        
    
                /*angular.module('myApp', [])
                 .controller('myCtrl', ['$scope', function ($scope) {
                 $.get("Registro", function (data) {
                 $scope.data = ["carro", "camioneta"];
                 // $scope.data = data[0];
                 });
                 }]);
                 
                 */
    </script>
</body>