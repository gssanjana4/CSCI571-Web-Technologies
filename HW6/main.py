from flask import Flask, request
from newsapi import NewsApiClient

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
@app.route('/home', methods=['GET', 'POST'])
def hw6():
    return app.send_static_file('html_files/home.html')


@app.route('/slideshows', methods=['GET', 'POST'])
def slideshows():
    news_api = NewsApiClient(api_key="563473644b03488ba36a8c0e366c5131")
    top_headlines = news_api.get_top_headlines(language='en', country='us')

    temp = filteroutheadlines(top_headlines)
    top_headlines["articles"] = temp
    top_headlines["totalResults"] = len(temp)
    # print(top_headlines)
    word_cloud_stopwords = {}

    with open('static/stopwords_en.txt', 'r') as f:
        for word in f:
            word = word.split()
            word_cloud_stopwords[word[0]] = 0

    print(word_cloud_stopwords)
    word_clouds = {}
    for article in top_headlines['articles']:
        t = article['title'].strip().split()
        for word in t:
            word = word.lower()
            if word not in word_cloud_stopwords:
                print(word)
                if word in word_clouds:
                    word_clouds[word] += 1
                else:
                    word_clouds[word] = 1


    word_clouds = sorted(word_clouds.items(), key=lambda word: word[1], reverse=True)
    print(word_clouds)
    # print(dict(list(word_clouds.items())[0:30]))
    top_headlines['word_clouds'] = word_clouds[0:30]
    # print(top_headlines)
    return top_headlines


@app.route('/CNNnews', methods=['GET', 'POST'])
def CNNnews():
    news_api = NewsApiClient(api_key="563473644b03488ba36a8c0e366c5131")
    cnn_headlines = news_api.get_top_headlines(sources="cnn", language='en')
    temp = filteroutheadlines(cnn_headlines)
    cnn_headlines["articles"] = temp
    cnn_headlines["totalResults"] = len(temp)
    # print(cnn_headlines)
    return cnn_headlines


@app.route('/Foxnews', methods=['GET', 'POST'])
def Foxnews():
    news_api = NewsApiClient(api_key="563473644b03488ba36a8c0e366c5131")
    fox_headlines = news_api.get_top_headlines(sources="fox-news", language='en')
    temp = filteroutheadlines(fox_headlines)
    fox_headlines["articles"] = temp
    fox_headlines["totalResults"] = len(temp)
    # print(fox_headlines)
    return fox_headlines


@app.route('/category', methods=['GET', 'POST'])
def category():
    news_api = NewsApiClient(api_key="563473644b03488ba36a8c0e366c5131")
    category = request.args.get('cat')
    if (category != "all"):
        category = news_api.get_sources(category=category, language="en", country="us")
    else:
        category = news_api.get_sources(language="en", country="us")

    # print(category)
    return category


@app.route('/advancedsearch', methods=['GET', 'POST'])
def advancedsearch():
    try:
        news_api = NewsApiClient(api_key="563473644b03488ba36a8c0e366c5131")
        keyword = request.args.get('keyword')
        datefrom = request.args.get('datefrom')
        dateto = request.args.get('dateto')
        source = request.args.get('source')
        if (source != "all"):
            advancedsearch = news_api.get_everything(q=keyword, sources=source, from_param=datefrom, to=dateto,
                                                     language="en", sort_by="publishedAt", page_size=30)
        else:
            advancedsearch = news_api.get_everything(q=keyword, from_param=datefrom, to=dateto, language="en",
                                                     sort_by="publishedAt", page_size=30)
        temp = filteroutheadlines(advancedsearch)
        advancedsearch["articles"] = temp
        advancedsearch["totalResults"] = len(temp)
        # print(advancedsearch)
        return advancedsearch
    except Exception as e:
        return eval(str(e))


def filteroutheadlines(d):
    a = d["articles"]
    ans = []
    for et in a:
        check = True
        for key in et:
            if et[key] is None or et[key] == 'null' or et[key] == '':
                check = False
        if check == True:
            ans.append(et)
    # print(ans)
    return ans


if __name__ == '__main__':
    app.run()
