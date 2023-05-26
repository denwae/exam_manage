<script>
    import ExamList from "$lib/ExamList.svelte";
    import ExamUpload from "../lib/ExamUpload.svelte";

    /** @type {import('./$types').PageData} */
    export let data;

    async function getExams(){
        const res = await fetch("http://localhost:8080/exams")
        const json = await res.json()
        console.log(json)
    }
</script>


<h1>Exam Manage</h1>

<ExamUpload />

<ExamList exams={data.exams} />

