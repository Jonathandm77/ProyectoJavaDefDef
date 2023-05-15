let cards = document.querySelectorAll(".user-card");

for (card of cards) {
	if (card.querySelector(".image") === null) {
		card.style.height = "100px";
	} else {
		card.style.height = ""
	}
}