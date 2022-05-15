var index = (function (){

    return{

        conectar : function (){
            fetch("http://localhost:8080/NiuBank2_0_war_exploded/api/").then(response => response.json()).then(json => alert(json))

        },

        infoUser : function () {
            let info = JSON.parse(localStorage.getItem("infoUser"))
            $('#nombre').html(info.nombre)
            $('#fondos').html(info.fondos)
        }

    }


})();
