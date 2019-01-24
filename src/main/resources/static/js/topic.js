$(function () {
    getCommentsByTopicId();
    $('#idButtonReply').click(function () {
        addReply();
    });

});
function getCommentsByTopicId () {
    var id = $('#idInputTopic').val();
    $.ajax({
        url: '/topics/'+id+'/comments',
        type: 'GET',
        // data: 'id=' + id,
        dataType: 'html',
        success: function (data) {
            $('#commentsId').html(data);
        }

    })

}
function addReply () {
    var idInputTopic=$('#idInputTopic').val();
    var idInputDesc=$('#idInputDesc').val();
    $.ajax({
        url: '/user/add-comment',
        type: "POST",
        data: 'id-topic='+idInputTopic+'&desc='+idInputDesc,
        success: function () {
            getCommentsByTopicId();
            $('#idInputDesc').val("");

        },
        error: function () {
            alert("Internal Error");

        }


    })
}