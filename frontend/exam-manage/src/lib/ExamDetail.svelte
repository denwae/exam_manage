<script>
    import ExamUpload from "$lib/ExamUpload.svelte";
    export let exam;

    async function download(){
        const res = await fetch(`/api/exams/${exam.examId}/file`);
        const blob = await res.blob();
        const link = window.URL.createObjectURL(blob);

        let a = document.createElement("a");
        a.setAttribute("download", `${exam.subject}_${exam.year}.pdf`);
        a.setAttribute("href", link);
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }
</script>

<div class="card w-full bg-base-200 border-accent my-1">
    <div class="card-body p-5">
        <div class="flex">
            <h2 class="card-title grow">{ exam.subject }</h2>

            <span class="badge badge-secondary badge-outline justify-end">
                {exam.year}
            </span>
        </div>
        <div class="grid grid-cols-2">
            <div>
                <ExamUpload examId={exam.examId} />
            </div>
            <div class="card-actions justify-end">
                <button class="btn" on:click={download}>Klausur runterladen</button>
            </div>
        </div>

    </div>
</div>