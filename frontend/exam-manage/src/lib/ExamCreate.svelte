<script>
    import { exams } from "$lib/stores.js";

    let subject = '';
    let year = new Date().getFullYear();

    function createExam() {
        fetch('/api/exams', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                subject: subject,
                year: year,
            })
        }).then(async res => {
            if (res.ok) {
                const exam = await res.json();
                $exams = [...$exams, exam];
            }
        })
    }
</script>


<div tabindex="0" class="collapse collapse-arrow border border-base-300 bg-base-100 rounded-box my-5 bg-base-200">
    <input type="checkbox" />
    <div class="collapse-title font-medium">
        <h1>Klausur erstellen</h1>
    </div>
    <div class="collapse-content container mx-auto flex justify-center">
        <div>
            <form>
                <label>
                    Fach
                    <input id="exam-subject" name="subject" type="text" bind:value={subject}>
                </label>
                <label>
                    Jahr
                    <input id="exam-year" name="year" type="number" bind:value={year}>
                </label>
                <div class="justify-end btn">
                    <button on:click={createExam}>Erstellen</button>
                </div>
            </form>
        </div>
    </div>
</div>

