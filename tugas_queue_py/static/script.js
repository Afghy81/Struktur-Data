window.addEventListener("DOMContentLoaded", function () {
    const el = document.getElementById("app-data");

    if (!el) return;

    let calledData = null;
    let isEmpty = false;

    try {
        calledData = JSON.parse(el.dataset.called || "null");
        isEmpty = JSON.parse(el.dataset.empty || "false");
    } catch (e) {
        console.error("JSON parse error:", e);
        return;
    }

    // Jika tidak ada antrian
    if (isEmpty) {
        alert("Tidak ada antrian saat ini");
        return;
    }

    // Jika ada antrian → bunyikan
    if (calledData && calledData.nama) {
        let text = `Nomor antrian ${calledData.nomor}, atas nama ${calledData.nama}, silakan ke loket`;

        speak(text);
    }
});


// 🔊 FIX AGAR AUDIO TIDAK DIBLOK BROWSER
document.addEventListener("click", function () {
    speechSynthesis.resume();
});


function speak(text) {
    const speech = new SpeechSynthesisUtterance(text);

    speech.lang = "id-ID";
    speech.rate = 0.9;

    speechSynthesis.cancel();
    speechSynthesis.speak(speech);
}

function openModal() {
    const modal = document.getElementById("modal");
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById("modal");
    modal.style.display = "none";
}