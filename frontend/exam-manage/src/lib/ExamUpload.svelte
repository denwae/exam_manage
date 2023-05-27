<script>

    export let examId;
    let files;
    let collapse = false

    async function upload(){
        try {
            const formData = new FormData;
            formData.append('file', files[0]);
            const response = await fetch(`/api/exams/${examId}/file`, {
                method: 'POST',
                body: formData
            })
            const result = await response.json();
            console.log(result);
        }catch (e) {
            console.log(e)
        }
        collapse = false
    }
</script>

<div class="collapse collapse-arrow">
    <input id="upload-collapse" type="checkbox" bind:checked={collapse}>
    <div class="collapse-title">Klausur hochladen</div>
    <div class="collapse-content">
        <input id="exam-upload" type="file" class="file-input file-input-bordered" bind:files>
        <button class="btn" on:click={upload}>Hochladen</button>
    </div>
</div>