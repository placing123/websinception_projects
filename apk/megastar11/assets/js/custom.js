function get_match(id) {
    var id = id;   
    $.ajax({
        type : 'post',       
        url: SITE_URL+'/notification/get_contest_by_matchid',
        data : { id : id },
        success : function(data) {
            $('#contest_id').html(data);
        }
    });
};


function get_players() {
    var id = $('#team_id').val();   

    $.ajax({
        type : 'post',       
        url: SITE_URL+'/players/get_players_by_teamid',
        data : { id : id },
        success : function(data) {
        	$('#player_hide').hide();
            $('#player_show').html(data);
        }
    });
};


$(function() {
    $("#ReferralUpdate").validate(
    {
        rules: {  Rreferral: "required" },
        messages: {
            Rreferral: "Please enter your Referral code"
        },
        submitHandler: function(form) {
            var RuserId = $('#RuserId').val();   
            var Rreferral = $('#Rreferral').val();   
            $.ajax({
                type : 'post',       
                url: SITE_URL+'/user/UpdateReferral',
                data : { RuserId : RuserId,Rreferral:Rreferral },
                success : function(data) {
                    if(data == "Success")
                    {
                        $("#NewReferral"+RuserId).html(Rreferral);
                        $('#ReferralClose').trigger('click');
                        alertify.success("Referral Code Update successfully");
                    } 
                    else if(data == "Exist")
                    {
                        $("#errorReferral").text("Referral Code alredy Exist").css({"color":"red"});
                        alertify.error("Referral Code alredy Exist");
                    } 
                    else
                    {
                        alertify.error("Please try again Some error occured");
                    }   
                    
                }
            });
        }
    });
});

$(".referral_codeModel").click(function () {
    var email = $(this).attr('data-email');
    var user_id = $(this).attr('data-id');
    var referral = $("#NewReferral"+user_id).html(); //$(this).attr('data-code');
    $("#RUserEmail").html(email);
    $("#Rreferral").val(referral);
    $("#RuserId").val(user_id);
});

function ReferralExist()
{
    var RuserId = $('#RuserId').val();   
    var Rreferral = $('#Rreferral').val();   
     $.ajax({
        type : 'post',       
        url: SITE_URL+'/user/ReferralExist',
        data : { RuserId : RuserId,Rreferral:Rreferral },
        success : function(data) {
            if(data == "Success")
            {
                $("#errorReferral").text("Referral Code alredy Exist").css({"color":"red","display":"block"});
            } 
            else if(data == "No")
            {
                $("#errorReferral").css({"display":"none"});
            }
        }
    });
};


$('.ViewPoints').click(function () {
        var PId = $(this).attr('mainID');
        var PName = $(this).attr('PName');
        $.ajax({
            type : 'post', 
            url: SITE_URL+'match_players/p_info',
            data: {PId : PId},
            success : function(data) {
              var points =  JSON.parse(data);
              $('#playerId').val(PId);
              $('#PName').html(PName);
              $("#batting").val(points.batting_points);
              $("#bolling").val(points.bolling_points);
              $("#fielding").val(points.fielding_points);
        }
        });
    });
    

$('.TViewPoints').click(function () {
        var PId = $(this).attr('mainID');
        var PName = $(this).attr('PName');
        $.ajax({
            type : 'post', 
            url: SITE_URL+'match_players/tp_info',
            data: {PId : PId},
            success : function(data) {
              var points =  JSON.parse(data);
              $('#tplayerId').val(PId);
              $('#tpname').html(PName);
              $("#tbatting").val(points.batting_points);
              $("#tbolling").val(points.bolling_points);
              $("#tfielding").val(points.fielding_points);
              $("#second_batting").val(points.second_innings_batting);
              $("#second_bolling").val(points.second_innings_bolling);
              $("#second_fielding").val(points.second_innings_fielding);
        }
        });
    });