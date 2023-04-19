const http = new XMLHttpRequest
const url = "https://newsapi.org/v2/everything?q=DGT&apiKey=48108a4dda8a47538c4a2ae5503dd6b4"
http.open("GET", url)
http.send()

let body=document.querySelectorAll("body")[0]
let articleDiv=document.createElement("div")
articleDiv.setAttribute("id","news")
body.appendChild(articleDiv)

http.addEventListener('load', (data) => { //para cada usuario
    const dataJSON = JSON.parse(data.target.response)
        for (let article of dataJSON.articles) {
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
        }
   
})
