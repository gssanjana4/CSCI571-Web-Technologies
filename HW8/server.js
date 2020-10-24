const express = require('express');
const app = express();
var request = require('request');
var path = require('path');
app.use(express.static(path.join(__dirname,"/build")));

var url1 ="https://api.nytimes.com/svc/topstories/v2/home.json?api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O"
app.get("/api/topstories", (req, res)=> {
request(url1, {json:true}, function (error, response, body) {
  if (!error && response.statusCode == 200) {
    res.json(body) // Print the google web page.
  }
})
});

var urltechnologyny = "https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O"
app.get("/api/urltechnologyny", (req, res)=> {
    request(urltechnologyny, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});

var urlsportsny = "https://api.nytimes.com/svc/topstories/v2/sports.json?api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O"
app.get("/api/urlsportsny", (req, res)=> {
    request(urlsportsny, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});
var urlpoliticsny = "https://api.nytimes.com/svc/topstories/v2/politics.json?api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O"
app.get("/api/urlpoliticsny", (req, res)=> {
    request(urlpoliticsny, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});
var urlworldny = "https://api.nytimes.com/svc/topstories/v2/world.json?api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O"
app.get("/api/urlworldny", (req, res)=> {
    request(urlworldny, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});
var urlbusinessny = "https://api.nytimes.com/svc/topstories/v2/business.json?api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O"
app.get("/api/urlbusinessny", (req, res)=> {
    request(urlbusinessny, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});


app.get("/api/urlnydetails", (req, res)=> {
    var urlnydetails = 'https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=web_url:("'+req.query.article+'")&api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O'
    request(urlnydetails, {json:true}, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.json(body) // Print the google web page.
    }
})
});

app.get("/api/urlnysearch", (req, res)=> {
  var urlnysearch = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="+req.query.articlesearch+"&api-key=GMERZqvq2AackGH8qTTDpoIKQXiQqn7O"
  request(urlnysearch, {json:true}, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      res.json(body) // Print the google web page.
  }
})
});

var url2 ="https://content.guardianapis.com/search?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&section=(sport|business|technology|politics)&show-blocks=all"
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

app.get("/api/guardiandetails", (req, res)=> {
    var guardiandetails= "https://content.guardianapis.com/"+req.query.article+"?api-key=9956d4cb-7b6c-4ff9-a2b4-5180ce4afcaf&show-blocks=all";
    request(guardiandetails, {json:true}, function (error, response, body) {
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
const port = process.env.PORT || 5000;

app.listen(port, () => console.log(`Server Started on port ${port}`));

app.get("/*",(req,res)=>{
  res.sendFile(path.join(__dirname,"/build/index.html"))
});