<script>
    export let exams;
</script>

<ul>
    {#each exams as exam}
        <li>{exam.subject}</li>
    {/each}
</ul>
