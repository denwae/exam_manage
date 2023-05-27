<script>
    import ExamList from "$lib/ExamList.svelte";
    import ExamUpload from "$lib/ExamCreate.svelte";
    import { exams } from "$lib/stores.js"

    /** @type {import('./$types').PageData} */
    export let data;
    $exams = data.exams;
</script>


<h1 class="text-4xl font-bold">Exam Manage</h1>

<ExamUpload />

<ExamList />

