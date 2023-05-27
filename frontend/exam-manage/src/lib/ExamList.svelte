<script>
    import ExamDetail from "$lib/ExamDetail.svelte";

    import { exams } from "$lib/stores.js";
</script>

{#each $exams as exam}
    <ExamDetail {exam}/>
{/each}

