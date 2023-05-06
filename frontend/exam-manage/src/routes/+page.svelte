<script lang="ts">
    let count = 0;

    console.log("Runs")

    function incrementCount() {
        console.log("got clicked")
        count += 1;
    }
</script>

<h1>home</h1>
<p>this is the about page.</p>

<button on:click={incrementCount}>
    Clicked {count} {count === 1 ? 'time' : 'times'}
</button>