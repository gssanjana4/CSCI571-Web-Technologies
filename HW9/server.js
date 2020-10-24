const express = require('express');
const app = express();
var request = require('request');
var path = require('path');
app.use(express.static(path.join(__dirname,"/build")));
const googleTrends = require('google-trends-api');


var url2 ="https://content.guardianapis.com/search?order-by=newest&show-fields=starRating,headline,thumbnail,short-url&api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf"
app.get("/api/guardian", (req, res)=> {
request(url2, {json:true}, function (error, response, body) {
  if (!error && response.statusCode == 200) {
    res.json(body) // Print the google web page.
  }
})
});

var urltechguardian = "https://content.guardianapis.com/technology?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all"
app.get("/api/urltechguardian", (req, res)=> {
    request(urltechguardian, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});

var urlpoliticsguardian = "https://content.guardianapis.com/politics?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all"
app.get("/api/urlpoliticsguardian", (req, res)=> {
    request(urlpoliticsguardian, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});

var urlsportsguardian = "https://content.guardianapis.com/sport?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all"
app.get("/api/urlsportsguardian", (req, res)=> {
    request(urlsportsguardian, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});

var urlworldguardian = "https://content.guardianapis.com/world?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all"
app.get("/api/urlworldguardian", (req, res)=> {
    request(urlworldguardian, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});
var urlbusinessguardian = "https://content.guardianapis.com/business?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all"
app.get("/api/urlbusinessguardian", (req, res)=> {
    request(urlbusinessguardian, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});


var urlscienceguardian = "https://content.guardianapis.com/science?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all"
app.get("/api/urlscienceguardian", (req, res)=> {
    request(urlscienceguardian, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});

app.get("/api/details", (req, res)=> {
 var details= "https://content.guardianapis.com/"+req.query.article+"?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all"
    request(details, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});

app.get("/api/guardianlatest", (req, res)=> {
  var guardianlatest = "https://content.guardianapis.com/search?order-by=newest&show-fields=starRating,headline,thumbnail,short-url&api-key=api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf";
  request(guardianlatest, {json:true}, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      res.json(body) // Print the google web page.
  }
  })
  });
app.get("/api/guardiansearch", (req, res)=> {
var guardiansearch = "https://content.guardianapis.com/search?q="+req.query.articlesearch+"&api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all";
request(guardiansearch, {json:true}, function (error, response, body) {
  if (!error && response.statusCode == 200) {
    res.json(body) // Print the google web page.
}
})
});

app.get("/trends", (req, res)=>{
  googleTrends.interestOverTime({keyword: req.query.trends,startTime: new Date("2019-06-01")})
  .then(function(results){
  res.send(results);  
})
  .catch(function(err){
    console.error(err);
  });
});
const port = process.env.PORT || 5000;

app.listen(port, () => console.log(`Server Started on port ${port}`));

app.get("/*",(req,res)=>{
  res.sendFile(path.join(__dirname,"/build/index.html"))
});