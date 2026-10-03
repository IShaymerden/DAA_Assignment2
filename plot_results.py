import os
import pandas as pd
import matplotlib.pyplot as plt

CSV_PATH = "results/results.csv"
PLOTS_DIR = "results/plots"

os.makedirs(PLOTS_DIR, exist_ok=True)

df = pd.read_csv(CSV_PATH)

df.columns = df.columns.str.strip()

for column in ["workload", "variant", "structure"]:
    df[column] = df[column].astype(str).str.strip()


def save_time_plot(workload, variant=None):
    data = df[df["workload"] == workload].copy()

    if variant is not None:
        data = data[data["variant"] == variant]

    if data.empty:
        return

    plt.figure()

    for structure in data["structure"].unique():
        part = data[data["structure"] == structure].sort_values("n")

        plt.plot(
            part["n"],
            part["time_ms"],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel("Time (ms)")

    title = f"{workload} - Time vs n"

    if variant is not None:
        title += f" ({variant})"

    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.tight_layout()

    filename = workload

    if variant is not None:
        filename += f"_{variant}"

    filename += "_time.png"

    plt.savefig(
        os.path.join(PLOTS_DIR, filename),
        dpi=200
    )

    plt.close()


def save_metric_plot(workload, metric, variant=None):
    data = df[df["workload"] == workload].copy()

    if variant is not None:
        data = data[data["variant"] == variant]

    if data.empty:
        return

    if data[metric].fillna(0).sum() == 0:
        return

    plt.figure()

    for structure in data["structure"].unique():
        part = data[data["structure"] == structure].sort_values("n")

        plt.plot(
            part["n"],
            part[metric],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel(metric.capitalize())

    title = f"{workload} - {metric.capitalize()} vs n"

    if variant is not None:
        title += f" ({variant})"

    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.tight_layout()

    filename = workload

    if variant is not None:
        filename += f"_{variant}"

    filename += f"_{metric}.png"

    plt.savefig(
        os.path.join(PLOTS_DIR, filename),
        dpi=200
    )

    plt.close()


# W1 - Random Access
save_time_plot("W1")
save_metric_plot("W1", "steps")
save_metric_plot("W1", "moves")
save_metric_plot("W1", "comparisons")

# W2 - Search
save_time_plot("W2")
save_metric_plot("W2", "steps")
save_metric_plot("W2", "moves")
save_metric_plot("W2", "comparisons")

# W3 - Head
save_time_plot("W3", "head")
save_metric_plot("W3", "steps", "head")
save_metric_plot("W3", "moves", "head")
save_metric_plot("W3", "comparisons", "head")

# W3 - Middle
save_time_plot("W3", "middle")
save_metric_plot("W3", "steps", "middle")
save_metric_plot("W3", "moves", "middle")
save_metric_plot("W3", "comparisons", "middle")

# W4 - Priority Processing
save_time_plot("W4")
save_metric_plot("W4", "steps")
save_metric_plot("W4", "moves")
save_metric_plot("W4", "comparisons")

print("Plots created successfully in results/plots/")