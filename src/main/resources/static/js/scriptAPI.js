const http = new XMLHttpRequest
const url = "https://newsapi.org/v2/everything?q=DGT&apiKey=48108a4dda8a47538c4a2ae5503dd6b4"
http.open("GET", url)
http.send()

let body = document.querySelectorAll("body")[0]
let articleDiv = document.createElement("div")
articleDiv.setAttribute("id", "news")
body.appendChild(articleDiv)

let articlesLoaded = 0
const articlesPerPage = 10

function loadMoreArticles() {
	const newArticlesLoaded = articlesLoaded + articlesPerPage
	const newUrl = `https://newsapi.org/v2/everything?q=DGT&apiKey=48108a4dda8a47538c4a2ae5503dd6b4&pageSize=${articlesPerPage}&page=${(newArticlesLoaded / articlesPerPage) + 1}`
	const newHttp = new XMLHttpRequest
	newHttp.open("GET", newUrl)
	newHttp.send()
	newHttp.addEventListener('load', (data) => { //para cada usuario
		const dataJSON = JSON.parse(data.target.response)
		for (let article of dataJSON.articles) {
			let articleItem = document.createElement("div")
			articleItem.setAttribute("class", "card article oscuro-panel")
			let articleImg = document.createElement("img")
			articleImg.setAttribute("src", article.urlToImage)
			articleImg.alt="sss"
			let articleContent = document.createElement("div")
			articleContent.setAttribute("class", "articleContent blue-letter-light")
			let articleHeader = document.createElement("h2")
			let linkHeader = document.createElement("a")
			linkHeader.setAttribute("href", article.url)
			linkHeader.textContent = article.title
			articleHeader.appendChild(linkHeader)
			let articleText = document.createElement("p")
			articleText.setAttribute("class", "lead")
			articleText.textContent = article.description
			articleItem.appendChild(articleImg)
			articleContent.appendChild(articleHeader)
			articleContent.appendChild(articleText)
			articleItem.appendChild(articleContent)
			articleItem.style.marginTop = "5px"
			articleDiv.appendChild(articleItem)
		}
		
			if (dataJSON.articles.length >= articlesPerPage) {
		let deleteButton=document.getElementById("loadMore")
		if(deleteButton!=null)
		deleteButton.remove()
		let loadMoreButton = document.createElement("button")
		loadMoreButton.setAttribute("id", "loadMore")

		loadMoreButton.classList.add("btn", "btn-primary", "position-relative","bottom-0")
		loadMoreButton.textContent = "Cargar más"
		loadMoreButton.addEventListener("click", () => {
			loadMoreArticles()
		})
		articleDiv.appendChild(loadMoreButton)
	}
	})
	articlesLoaded = newArticlesLoaded
}

http.addEventListener('load', (data) => {
	const dataJSON = JSON.parse(data.target.response)
	for (let i = 0; i < articlesPerPage; i++) {
		let article = dataJSON.articles[i]
		let articleItem = document.createElement("div")
		articleItem.setAttribute("class", "card article oscuro-panel")
		let articleImg = document.createElement("img")
		articleImg.alt=article.title
		articleImg.setAttribute("src", article.urlToImage)
		let articleContent = document.createElement("div")
		articleContent.setAttribute("class", "articleContent blue-letter-light")
		let articleHeader = document.createElement("h2")
		let linkHeader = document.createElement("a")
		linkHeader.setAttribute("href", article.url)
		linkHeader.textContent = article.title
		articleHeader.appendChild(linkHeader)
		let articleText = document.createElement("p")
		articleText.setAttribute("class", "lead")
		articleText.textContent = article.description
		articleItem.appendChild(articleImg)
		articleContent.appendChild(articleHeader)
		articleContent.appendChild(articleText)
		articleItem.appendChild(articleContent)
		articleItem.style.marginTop = "5px"
		articleDiv.appendChild(articleItem)
	}
	if (dataJSON.articles.length > articlesPerPage) {
		let loadMoreButton = document.createElement("button")
		loadMoreButton.setAttribute("id", "loadMore")

		loadMoreButton.classList.add("btn", "btn-primary", "position-relative","bottom-0")
		loadMoreButton.textContent = "Cargar más"
		loadMoreButton.addEventListener("click", () => {
			loadMoreArticles()
			loadMoreButton.remove()
		})
		articleDiv.appendChild(loadMoreButton)
	}

})

document.addEventListener('DOMContentLoaded', function() {
            document.title = 'Inicio';
            })
