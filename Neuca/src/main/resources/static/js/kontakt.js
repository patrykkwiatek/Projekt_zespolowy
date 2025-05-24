document.getElementById("entryForm").addEventListener("submit", function(event) {
    event.preventDefault();

    //Pobranie danych z formularza
    const imieNazwisko = document.getElementById("imie-nazwisko").value;
    const email = document.getElementById("email").value;
    const temat = document.getElementById("temat").value;
    const wiadomosc = document.getElementById("wiadomosc").value;

    if (!imieNazwisko || !email || !temat || !wiadomosc) {
        alert("Proszę wypełnić pola!");
        return;
    }

    //Przetwarzanie danych (wyświetlanie ich na stronie lub wysyłanie na serwer)
    const dane = {
        imieNazwisko: imieNazwisko,
        email: email,
        temat: temat,
        wiadomosc: wiadomosc
    };

    //Wyświetlanie danych na stronie (lub wysyłanie na serwer)
    console.log("Dane z formularza:", dane);

    alert("Formularz został wysłany!");
});