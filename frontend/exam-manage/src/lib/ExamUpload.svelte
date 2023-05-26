<script>

    function upload() {

    }
</script>

<label for="create-exam" class="btn">Klausur hochladen</label>

<input type="checkbox" id="create-exam" class="modal-toggle"/>

<div class="modal">
    <div class="modal-box container mx-auto flex justify-center">
        <div>
            <label for="create-exam" class="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
            <h1>Klausur erstellen</h1>
            <form>
                <label>
                    Fach
                    <input name="subject" type="text">
                </label>
                <label>
                    Jahr
                    <input name="year" type="number" value="{new Date().getFullYear()}">
                </label>
                <div class="justify-end modal-action">
                    <button for="create-exam">Erstellen</button>
                </div>
            </form>
        </div>
    </div>
</div>

