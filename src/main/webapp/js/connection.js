

var connection = (function (){


    return{

        conectar : function (user, password){
            let header = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email: user,
                    password: password})
            }
            console.log(user)
            console.log(password)
            fetch ("http://localhost:8080/NiuBank2_0_war_exploded/api/v2/Banco/Login/user", header)
                .then(response => response.json())
                .then(function (token){
                    localStorage.setItem("token", JSON.stringify(token))
                    console.log("pedir informacion")
                    let tokenizer = JSON.parse(localStorage.getItem('token'))
                    const requestOptions = {
                        method: 'GET',
                        headers: {'Authorization': tokenizer.token }
                    }
                    console.log(localStorage.getItem('token'))
                    fetch("http://localhost:8080/NiuBank2_0_war_exploded/api/v2/Banco/infoUser", requestOptions)
                        .then(response => response.json())
                        .then(function (infoUser){
                            localStorage.setItem("infoUser", JSON.stringify(infoUser))
                            if(infoUser.rol === "ADMIN"){
                                location.href = 'lobyAdmin.html'
                            }else if(infoUser.rol === "AUDITOR"){
                                location.href = 'lobyAuditor.html'
                            }else if(infoUser.rol === "USER"){
                                location.href = 'lobyUser.html'
                            }
                        })
                })
        },

        registrarUser: function (cedula){
            console.log('entre')
            let tokenizer = JSON.parse(localStorage.getItem('token'))
            let header = {method: 'GET',
                headers: { 'Authorization': tokenizer.token }
            }
            fetch( "http://localhost:8080/NiuBank2_0_war_exploded/api/v2/Banco/registrar/User/"+cedula, header)
                .then(response => response.json())
                .then( function (data){
                    if(data){
                        alert("Usuario Registrado")
                    }
                })
        },

        transferencia: function (cedulaDestino, monto){
            let info = JSON.parse(localStorage.getItem("cedula"))
            fetch("http://localhost:4567/Transferencia?ccOrigen="+info.cedula+"&ccDestino="+cedulaDestino+"&monto="+monto)
                .then(response => response.json())
                .then( function (data){
                    console.log(data.transferencia)
                    if (data){
                        alert("transerencia realizada")
                    }
                })
        },

        loadMonto : function(){
            let info = JSON.parse(localStorage.getItem("cedula"));
            fetch("http://localhost:4567/VerFondos?cedula="+info.cedula)
            .then(response => response.json())
            .then(function(data){
                console.log(data)
                $('#nombre').html(data.nombre)
                $('#monto').html(data.fondos)

            })

        },

        verTransferencia : function(){
            fetch("http://localhost:4567/verTransferencia?transacciones=users")
                .then(response => response.json())
                .then(function(data){
                    let html = "<tr>";
                    let monto = 0;
                    let numero = 0;
                    for ( const property in data){
                        console.log(data[property])
                        monto += data[property].cantidad
                        numero +=1
                        html += "<td>" + data[property].cedulaemisor + "</td>"
                        html += "<td>" + data[property].cedulareceptor + "</td>"
                        html += "<td>" + data[property].cantidad + "</td>"
                        html += "</tr>"
                        $('#bodyTable').html(html)
                    }
                    $('#monto').html(monto)
                    $('#total').html(numero)



                })
        },

        modificarMonto :  function (cedula, cantidad){
            console.log(cedula)
            console.log(cantidad)
            fetch("http://localhost:4567/modificarMonto?cedula="+cedula+"&cantidad="+cantidad)
                .then(response => response.json())
                .then(function (data){

                    if (data.modificacion){
                        alert("monto modificado")
                    }
                })
        },


        solicitarSobregiro: function (cedula, monto){
            fetch("http://localhost:4567/solicitarSobregiro?cedula="+cedula+"&monto="+monto)
                .then(response => response.json())
                .then(function (data){
                    if(data){
                        alert("Sobregiro Solicitado")
                    }

            })

        },

        crearUser : function (cedula, nombre, apellido, correo, password){
            let header = {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    cedula:cedula,
                    nombre:nombre,
                    apellido:apellido,
                    correo:correo,
                    contrasena:password,
                    fondos:0,
                    rol:"USER"
                })
            }
            fetch("http://localhost:8080/NiuBank2_0_war_exploded/api/v2/Banco/crear/User",header)
                .then(response => response.json())
                .then( function (data){
                    if(data){
                        alert("cuenta creado con exito")
                        location.href = 'login.html'
                    }else {
                        alert("usted no esta registrado")
                    }
                })
        },

        mostrarAutorizaciones: function (){
            fetch("http://localhost:4567/mostrarAutorizaciones?autorizaciones=auto")
                .then(response => response.json())
                .then(function (data){
                    let html = "<tr>";
                    console.log(data)
                    for ( const property in data){
                        html += "<td>" + data[property].id+ "</td>"
                        html += "<td>" + data[property].cedula + "</td>"
                        html += "<td>" + data[property].monto + "</td>"
                        html += "<td> <button type='button'  onclick='connection.autorizar(\""+data[property].cedula+"\", \""+data[property].monto+"\")' >Aceptar!</button></td>";
                        html += "</tr>"
                        $('#body').html(html)
                    }
                })
        },

        autorizar: function (cedula, monto){
            console.log(cedula)
            console.log(monto)
            fetch("http://localhost:4567/autorizar?cedula="+cedula+"&monto="+monto)
                .then(response => response.json())
                .then(function (data){
                    alert("Monto Girado con exito")

                })
        }
    }
})();