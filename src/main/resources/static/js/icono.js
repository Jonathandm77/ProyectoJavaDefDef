document.addEventListener('DOMContentLoaded', function() {
	var iconLink = document.querySelector("link[rel~='icon']");

	if (!iconLink) {
		iconLink = document.createElement("link");
		iconLink.rel = "icon";
		document.head.appendChild(iconLink);
	}

	iconLink.href = "static/img/favicon.png";

})
