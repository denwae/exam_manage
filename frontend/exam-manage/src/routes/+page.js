/** @type {import('./$types').PageLoad} */
export async function load({fetch, params}) {
    const res = await fetch("/api/exams")
    const exams = await res.json()

    return {exams}
}