<script>
    import { exams } from "$lib/stores.js";
</script>

<ul>
    {#each $exams as exam}
        <li>{exam.subject}</li>
    {/each}
</ul>
