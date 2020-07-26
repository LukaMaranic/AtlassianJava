var exerciseApp = exerciseApp || {};

exerciseApp.getBooks = function(cb){
    AJS.$.ajax({
        url: AJS.contextPath() + "/rest/exercise/1.0/book",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            cb(data);
        }
    });
}

AJS.toInit(function(){ //synonymous to $(document).ready() in jQuery

    console.log("Booklist Panel JS loaded!");
    exerciseApp.getBooks(function(data){
        data.books.forEach(function(bookItem){
            var html = ExerciseApp.SoyTemplates.bookItemRow(bookItem);
            AJS.$("#booklistHandle").find("tbody").append(html);
        });
    });
});

function deleteBook(row){
    var id = row.id;
        AJS.$.ajax({
            url: AJS.contextPath() + "/rest/exercise/1.0/book/"+id,
            type: "DELETE",
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert("Book deleted")
                location.reload();
            }
        });
}
