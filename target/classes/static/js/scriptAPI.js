let body=document.querySelectorAll("body")[0]
let articleDiv=document.createElement("div")
articleDiv.setAttribute("id","news")
fetch("https://newsapi.org/v2/everything?q=DGT&apiKey=48108a4dda8a47538c4a2ae5503dd6b4")
  .then(response => response.json())
  .then(data => {
    data.articles.forEach(article => {
      let articleItem=document.createElement("div")
      articleItem.setAttribute("class","card article")
      let articleImg=document.createElement("img")
      articleImg.setAttribute("src",article.urlToImage)
      let articleContent=document.createElement("div")
      articleContent.setAttribute("class","articleContent")
      let articleHeader=document.createElement("h2")
      let linkHeader=document.createElement("a")
      linkHeader.setAttribute("href",article.url)
      linkHeader.textContent=article.title
      articleHeader.appendChild(linkHeader)
      let articleText=document.createElement("p")
      articleText.setAttribute("class","lead")
      articleText.textContent=article.description
      articleItem.appendChild(articleImg)
        articleContent.appendChild(articleHeader)
        articleContent.appendChild(articleText)
      articleItem.appendChild(articleContent)
      articleItem.style.marginTop="5px"
      articleDiv.appendChild(articleItem)
    });
    body.appendChild(articleDiv)
  })
  .catch(error => {
    console.error(error);
  });
