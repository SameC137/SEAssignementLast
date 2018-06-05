var express = require("express");
var app = express();
var fs = require("fs");
var bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({
    extended: true
}));

// parse application/json
app.use(bodyParser.json());

var articles;
app.get('/',function(request,response){
        response.header('Content-Type','text/html')
        response.sendFile(__dirname+"/"+"index.html");
})
app.use(function(req,res,next){
    fs.readFile(__dirname + "/" + "articles.json", 'utf8', function (err, data) {
        articles = JSON.parse(data.toString().trim());
        next();
    });
})
app.get('/checkExists', function (request, response) {

    
    var title = request.query["title"];
    var foundarticle = false;
    for (var i in articles) {
        if (i.toString().startsWith(title)) {
            foundarticle = true;
            break;
        }
    }
    if (foundarticle) {
        response.header('Content-Type','text/html')
        response.sendFile(__dirname+"/"+"exists.html");
    }else{
        
        response.header('Content-Type','text/html')
        response.sendFile(__dirname+"/"+"notexist.html");
    }
})

app.post('/addArticle', function (request, response) {
    var title = request.body["title"];
    var content = request.body["content"];
    if (content != "" && content != "") {
        fs.readFile('articles.json', 'utf8', function readFileCallback(err, data) {
            if (err) {
                console.log(err);
            } else {
                obj = JSON.parse(data.toString().trim()); //now it an object
                obj[title] = content.trim();
                json = JSON.stringify(obj); //convert it back to json
                fs.writeFile('articles.json', json, 'utf8', function (err) {
                    if (err) throw err;
                }); // write it back 
            }
        }); 
        response.redirect("/articles");
    } else {
        response.sendStatus(400)
    }
 })
 
 app.get('/articles',function(request,response){
    
     
    var foundArticles=false;
    var html="<html><title>Articles</title><body>"
    for( var i in articles){
        html="<article><h3>"+i+"</h3><br><p>"+articles[i]+"</p></article><br><br>"+html;
        foundArticles=true;
        
    }
    if(!foundArticles){
        html+="<h3>No Articles Found</h3>"
    }
    html+="</body></html>";
    
    response.header('Content-Type','text/html')
    response.send(html);
})


app.listen(8000);